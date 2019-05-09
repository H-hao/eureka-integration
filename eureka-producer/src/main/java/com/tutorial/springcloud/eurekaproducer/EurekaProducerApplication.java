package com.tutorial.springcloud.eurekaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// EnableEurekaClient 与 EnableDiscoveryClient 现在都不需要手动配置，因为其已根据引入的包来进行自动注册；
public class EurekaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerApplication.class, args);
    }
}
