package com.tutorial.springcloud.eurekaribbon;

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
	private HelloService helloService;

	@GetMapping("/h")
	public String hello(@RequestParam String name) {
		return helloService.hello(name);
	}

}
