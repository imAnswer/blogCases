package org.example.spring.AOPAnnotation.controller;


import cn.itcast.service.IpcountService;
import org.example.spring.AOPAnnotation.request.UserRequest;
import org.example.spring.AOPAnnotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-04 15:34
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IpcountService ipcountService;

    @GetMapping("/user")
    public void user() {
        ipcountService.count();
        userService.print(new UserRequest(null, "1"));
    }

}
