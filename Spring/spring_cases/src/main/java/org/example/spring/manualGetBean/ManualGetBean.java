package org.example.spring.manualGetBean;

import org.example.spring.manualGetBean.service.AdultService;
import org.example.spring.manualGetBean.utils.SpringContextUtils;

/**
 * @author liushaoya
 * @since 2024-12-10 22:49
 */
public class ManualGetBean {

    /**
     * 会报错，因为这不是一个spring方法，不会启动容器，因而会报空指针
     * @param args
     */
    public static void main(String[] args) {
        SpringContextUtils.getBean(AdultService.class).adultTest();
    }

}
