package cn.itcast.service;

import cn.itcast.properties.IpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liushaoya
 * @since 2025-10-05 10:48
 */
public class IpcountService {

        private Map<String, Integer> ipCountMap = new HashMap<>();

        @Autowired
        private HttpServletRequest httpServletRequest;

    @Autowired
    private IpProperties ipProperties;

    @Scheduled(cron = "0/5 * * * * ?")
    public void print() {
        if (ipProperties.getModel().equals(IpProperties.LogModel.DETAIL.getValue())) {
            System.out.println("            IP访问监控");
            System.out.println("+-----ip-address-----+--num--+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(String.format("|%18s |%5d |", key, value));
            }
            System.out.println("+--------------------+-------+");
        } else if (ipProperties.getModel().equals(IpProperties.LogModel.SIMPLE.getValue())) {
            System.out.println("            IP访问监控");
            System.out.println("+-----ip-address-----+");
            for (String key : ipCountMap.keySet()) {
                System.out.println(String.format("|%18s |", key));
            }
            System.out.println("+--------------------+");
        }

        if(ipProperties.getCycleReset()) {
            ipCountMap.clear();
        }
    }

    public void count() {
        //每次调用当前操作，就记录当前访问IP
        //1.获取当前操作的IP地址
        String ip = httpServletRequest.getRemoteAddr();

        //2.根据IP地址从Map取值，并递增
        Integer count = ipCountMap.get(ip);
        if(count == null) {
            ipCountMap.put(ip, 1);
        }else {
            ipCountMap.put(ip, count + 1);
        }
    }

    public static void main(String[] args) {
        new IpcountService().print();
    }
}
