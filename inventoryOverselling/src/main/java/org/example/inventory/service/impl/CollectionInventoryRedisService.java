package org.example.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.InventoryResponse;
import org.example.inventory.service.InventoryService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.example.inventory.constant.ResponseCode.BIZ_ERROR;
import static org.example.inventory.constant.ResponseCode.DUPLICATED;

/**
 * @author liushaoya
 * @since 2025-10-29 10:35
 */
@Service
@Slf4j
public class CollectionInventoryRedisService implements InventoryService {

    @Autowired
    private RedissonClient redissonClient;

    private static final String INVENTORY_KEY = "clc:inventory:";

    private static final String INVENTORY_STREAM_KEY = "blb:inventory:stream:";

    public static final String ERROR_CODE_INVENTORY_NOT_ENOUGH = "INVENTORY_NOT_ENOUGH";
    public static final String ERROR_CODE_INVENTORY_IS_ZERO = "INVENTORY_IS_ZERO";
    public static final String ERROR_CODE_KEY_NOT_FOUND = "KEY_NOT_FOUND";
    public static final String ERROR_CODE_OPERATION_ALREADY_EXECUTED = "OPERATION_ALREADY_EXECUTED";

    @Override
    public InventoryResponse init(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            inventoryResponse.setSuccess(true);
            inventoryResponse.setResponseCode(DUPLICATED.name());
            return inventoryResponse;
        }
        redissonClient.getBucket(getCacheKey(request), StringCodec.INSTANCE).set(request.getInventory());
        inventoryResponse.setSuccess(true);
        inventoryResponse.setGoodsId(request.getGoodsId());
        inventoryResponse.setGoodsType(request.getGoodsType());
        inventoryResponse.setIdentifier(request.getIdentifier());
        inventoryResponse.setInventory(request.getInventory());
        return inventoryResponse;
    }

    @Override
    public Integer getInventory(InventoryRequest request) {
        String value = (String) redissonClient.getBucket(getCacheKey(request), StringCodec.INSTANCE).get();
        return Integer.valueOf(value);
    }

    @Override
    public InventoryResponse decrease(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        String luaScript = """
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                
                local current = redis.call('get', KEYS[1])
                if current == false then
                    return redis.error_reply('KEY_NOT_FOUND')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                if tonumber(current) == 0 then
                    return redis.error_reply('INVENTORY_IS_ZERO')
                end
                if tonumber(current) < tonumber(ARGV[1]) then
                    return redis.error_reply('INVENTORY_NOT_ENOUGH')
                end
                
                local new = tonumber(current) - tonumber(ARGV[1])
                redis.call('set', KEYS[1], tostring(new))
                
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                
                -- 使用哈希结构存储日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "decrease",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
                
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE,
                    luaScript,
                    RScript.ReturnType.INTEGER,
                    Arrays.asList(getCacheKey(request), getCacheStreamKey(request)),
                    request.getInventory(),
                    "DECREASE_" + request.getIdentifier())).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            log.error("decrease error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_INVENTORY_NOT_ENOUGH)) {
                inventoryResponse.setResponseCode(ERROR_CODE_INVENTORY_NOT_ENOUGH);
            } else if (e.getMessage().startsWith(ERROR_CODE_INVENTORY_IS_ZERO)) {
                inventoryResponse.setResponseCode(ERROR_CODE_INVENTORY_IS_ZERO);
            } else if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    @Override
    public InventoryResponse increase(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        String luaScript = """
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                
                local current = redis.call('get', KEYS[1])
                if current == false then
                    return redis.error_reply('key not found')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                                
                local new = (current == nil and 0 or tonumber(current)) + tonumber(ARGV[1])
                redis.call('set', KEYS[1], tostring(new))
                                
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                                
                -- 使用哈希结构存储日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "increase",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
                                
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                    luaScript,
                    RScript.ReturnType.INTEGER,
                    Arrays.asList(getCacheKey(request), getCacheStreamKey(request)),
                    request.getInventory(), "INCREASE_" + request.getIdentifier())).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            log.error("increase error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    @Override
    public void invalid(InventoryRequest request) {
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            redissonClient.getBucket(getCacheKey(request)).delete();
        }

        if (redissonClient.getBucket(getCacheStreamKey(request)).isExists()) {
            // 让流水记录的过期时间设置为24小时后，这样可以避免流水记录立即过期，对账出现问题
            redissonClient.getBucket(getCacheStreamKey(request)).expire(Instant.now().plus(24, ChronoUnit.HOURS));
        }
    }

    @Override
    public String getInventoryDecreaseLog(InventoryRequest request) {
        return "";
    }

    @Override
    public String getInventoryIncreaseLog(InventoryRequest request) {
        return "";
    }

    @Override
    public List<String> getInventoryDecreaseLogs(InventoryRequest request) {
        return null;
    }

    @Override
    public Long removeInventoryDecreaseLog(InventoryRequest request) {
        return 0l;
    }

    protected String getCacheKey(InventoryRequest request) {
        return INVENTORY_KEY + request.getGoodsId();
    }

    protected String getCacheStreamKey(InventoryRequest request) {
        return INVENTORY_STREAM_KEY + request.getGoodsId();
    }
}
