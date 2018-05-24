package com.tutorial.springcloud.eurekaconsumerfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 * @date 2018/5/17
 */
@FeignClient(name = "eureka-producer", fallback = HelloFallback.class)
public interface HelloClient {

	// 当userName没有被@RequestParam注解修饰时，会自动被当做request body来处理，即报错：Request method 'POST' not supported
	// Controller里面可以不加该注解修饰
	// 由于 Feign 是基于 Ribbon 实现的，所以它自带了客户端负载均衡功能，也可以通过 Ribbon 的 IRule 进行策略扩展

	// 尽量在 feignClient 中不要使用 requestMapping，可能会造成与 controller 中的 urlPattern 重复

	@GetMapping("/hello/")
	String hello(@RequestParam("name") String name);

}
