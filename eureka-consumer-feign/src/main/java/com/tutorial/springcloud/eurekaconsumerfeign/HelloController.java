package com.tutorial.springcloud.eurekaconsumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2018/5/17
 */
@RestController
@RequestMapping("/hello")
@RefreshScope
public class HelloController {
    @Autowired
    HelloClient helloClient;
    @Value("${config.hello}")
    String hello;

    @GetMapping("/h")
    public String hello(@RequestParam("name") String name) {
        return helloClient.hello(name + "! by feign" + " of " + hello);
    }
}
