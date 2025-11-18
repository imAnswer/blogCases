package cn.itcast.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liushaoya
 * @since 2025-10-05 16:12
 */
@Data
@ConfigurationProperties(prefix = "tools.ip")
public class IpProperties {

    /**
     * 日志显示周期
     */
    private Long cycle = 5L;

    /**
     * 是否周期内重置数据
     */
    private Boolean cycleReset = false;

    /**
     * 日志输出模式，detail：详细模式 simple：极简模式
     */
    private String model = LogModel.DETAIL.value;

    public enum LogModel {
        DETAIL("detail"),
        SIMPLE("simple");
        private String value;
        LogModel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
