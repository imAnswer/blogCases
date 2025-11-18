package org.example.spring.iocCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoaderListener;

/**
 *
 *@author liushaoya
 *@since 2024-12-10 13:29
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 依赖注入的作用：
     * 有了依赖注入，当一个类作为另一个类的属性时，可以无需手动new该对象属性，直接由IoC容器将该属性传递给对象
     * 如果没有依赖注入，则会报空指针异常
     * @return
     */
    @GetMapping("/ioccase")
    public String ioccase() {
        return studentService.getMessage();
    }
}
