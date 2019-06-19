package com.tutorial.springcloud.eurekaconsumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    @Value("${config.hello:not found}")
    String hello;

    @GetMapping("/h")
    public String hello(@RequestParam("name") String name) {
        return helloClient.hello(name + "! by feign" + " of " + hello);
    }

    @PostMapping(value = "/p", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String, Object> helloPost(@RequestBody(required = false) Map<String, Object> params) {
        return params;
    }
}
