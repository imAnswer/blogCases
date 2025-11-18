package org.example.spring.manualGetBean.controller;

import org.example.spring.manualGetBean.service.AdultService;
import org.example.spring.manualGetBean.service.ChildrenService;
import org.example.spring.manualGetBean.utils.SpringContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liushaoya
 * @since 2024-12-12 23:28
 */
@RestController
public class manualGetBeanController {

    /**
     * 当一个接口有两个实现类时，就不能再使用@Autowired注解来获取对应的bean了，应改为以下四种方式：
     * 1、根据@Resource
     * 2、根据@Primary
     * 3、根据@Qualifier
     * 4、手动获取bean对象
     * 以下方法就是手动获取bean对象的方法，注意bean的名称为类名首字母小写
     */
    @GetMapping("/test")
    public void test() {
        AdultService adultService = SpringContextUtils.getBean("adult02ServiceImpl", AdultService.class);
        adultService.adultTest();
        SpringContextUtils.getBean(ChildrenService.class).childrenTest();
    }
}
