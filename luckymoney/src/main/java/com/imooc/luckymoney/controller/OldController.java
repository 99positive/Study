package com.imooc.luckymoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lqb
 * on 2019/4/24.
 */
@Controller
public class OldController {

    @GetMapping("/old/hello")
    public String HelloWord(){
        return "index";
    }
}
