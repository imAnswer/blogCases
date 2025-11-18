package org.example.controller;

import org.example.entity.Result;
import org.example.entity.SensitiveTest;
import org.example.mapper.SensitiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-15 9:22
 */
@RestController
public class SensitiveController {

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @GetMapping("/sensitive")
    public Result<SensitiveTest> getSensitiveMessage() {
        return Result.success(sensitiveMapper.selectById(2L));
    }
}
