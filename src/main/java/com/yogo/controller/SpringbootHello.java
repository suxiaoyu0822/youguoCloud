package com.yogo.controller;




import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 苏晓雨
 * @date 2020/6/10 上午10:48
 * @Description
 */
@RequestMapping("/api")
@RestController
public class SpringbootHello {
    @RequestMapping("/hello")
    public String Hello() {
        String hello = "hello world!";
        System.out.println(hello);
        return hello;
    }
}
