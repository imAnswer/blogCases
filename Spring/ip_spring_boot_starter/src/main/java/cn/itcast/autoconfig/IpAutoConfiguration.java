package cn.itcast.autoconfig;

import cn.itcast.properties.IpProperties;
import cn.itcast.service.IpcountService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liushaoya
 * @since 2025-10-05 15:21
 */
@EnableScheduling
@EnableConfigurationProperties(IpProperties.class)
public class IpAutoConfiguration {
    @Bean
    public IpcountService ipcountService() {
        return new IpcountService();
    }
}
