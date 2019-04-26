package com.tutorial.springcloud.eurekaconsumerfeign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 * @date 2018/5/18
 */
@Component
public class HelloFallback implements HelloClient {

	@Override
	public String hello(@RequestParam("name")String name) {
		return "error with calling /hello/h?name=" + name;
	}
}
