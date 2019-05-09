package com.tutorial.springcloud.hystrixturbinestream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
public class HystrixTurbineStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineStreamApplication.class, args);
    }

    // // 继承
    // class TurbineController extends org.springframework.cloud.netflix.turbine.stream.TurbineController {
    //
    //     public TurbineController(PublishSubject<Map<String, Object>> hystrixSubject) {
    //         super(hystrixSubject);
    //     }
    //
    //     @Override
    //     @GetMapping(value = "/turbine.stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //     public Flux<String> stream() {
    //         return super.stream();
    //     }
    //
    // }
    // // 在main方法启动类里面添加初始化
    // @Bean
    // public org.springframework.cloud.netflix.turbine.stream.TurbineController turbineController(PublishSubject<Map<String, Object>> hystrixSubject) {
    //     return new TurbineController(hystrixSubject);
    // }
}
