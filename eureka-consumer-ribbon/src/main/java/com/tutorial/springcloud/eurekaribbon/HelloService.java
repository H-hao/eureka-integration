package com.tutorial.springcloud.eurekaribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @date 2018/5/18
 */
@Service
public class HelloService {
	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "helloFallback")
	public String hello(String name) {
		name += "!";
		// 直接用服务名eureka-producer取代了之前的具体的host:port
		// Spring Cloud Ribbon 有一个拦截器，它能够在这里进行实际调用的时候，自动的去选取服务实例，
		// 并将这里的服务名替换成实际要请求的 IP 地址和端口，从而完成服务接口的调用
		String url = "http://eureka-producer/hello/h?name=" + name;
		// 调用失败的例子
		// String url = "http://eureka-producer/hello/?name=" + name;
		return restTemplate.getForObject(url, String.class);
	}

	// 这里的 fallback 的参数应该与调用者相同，否则抛出 no such method；

	String helloFallback(String name) {
		return "error with calling hello?name=" + name + "! by ribbon";
	}

}
