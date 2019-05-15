package com.imooc.luckymoney.controller;

import com.imooc.luckymoney.LimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lqb
 * on 2019/4/22.
 */
@RestController
public class HelloController {

    @Autowired
    private LimitConfig limitConfig;

    @PostMapping({"/hello", "/hi"})
    public String say(@RequestParam(value="id", required = false, defaultValue = "99") Integer id) {
        return "说明：" + id;
    }
}
