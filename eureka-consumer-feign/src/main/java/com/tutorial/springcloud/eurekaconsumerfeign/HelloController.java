package com.tutorial.springcloud.eurekaconsumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
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
public class HelloController {
	@Autowired
	HelloClient helloClient;

	@GetMapping("/")
	public String hello(@RequestParam String name) {
		return helloClient.hello(name + "! by feign");
	}
}
