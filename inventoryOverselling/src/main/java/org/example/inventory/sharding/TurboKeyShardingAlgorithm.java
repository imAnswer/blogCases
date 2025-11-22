package org.example.inventory.sharding;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.example.inventory.idUtil.DistributeID;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 分片算法
 * @author liushaoya
 * @since 2025-11-22 10:48
 */
public class TurboKeyShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private Properties props;

    private static final String PROP_MAIN_COLUM = "mainColum";

    private static final String PROP_TABLE_COUNT = "tableCount";

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Collection<String> result = new HashSet<>();

        String mainColum = props.getProperty(PROP_MAIN_COLUM);
        // 获取分片键的值
        Collection<String> mainColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(mainColum);

        if (CollectionUtils.isNotEmpty(mainColums)) {
            for (String colum : mainColums) {
                String shardingTarget = calculateShardingTarget(colum);
                result.add(shardingTarget);
            }
            return getMatchedTables(result, availableTargetNames);
        }

        complexKeysShardingValue.getColumnNameAndShardingValuesMap().remove(mainColum);
        Collection<String> otherColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().keySet();
        if (CollectionUtils.isNotEmpty(otherColums)) {
            for (String colum : otherColums) {
                Collection<String> otherColumValues = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(colum);
                for (String value : otherColumValues) {
                    String shardingTarget = extractShardingTarget(value);
                    result.add(shardingTarget);
                }
            }
            return getMatchedTables(result, availableTargetNames);
        }

        return null;
    }

    private Collection<String> getMatchedTables(Collection<String> results, Collection<String> availableTargetNames) {
        Collection<String> matchedTables = new HashSet<>();
        for (String result : results) {
            matchedTables.addAll(availableTargetNames.parallelStream().filter(each -> each.endsWith(result)).collect(Collectors.toSet()));
        }
        return matchedTables;
    }

    private String extractShardingTarget(String orderId) {
        return DistributeID.getShardingTable(orderId);
    }

    private String calculateShardingTarget(String buyerId) {
        String tableCount = props.getProperty(PROP_TABLE_COUNT);
        return DistributeID.getShardingTable(buyerId, Integer.parseInt(tableCount));
    }

}
