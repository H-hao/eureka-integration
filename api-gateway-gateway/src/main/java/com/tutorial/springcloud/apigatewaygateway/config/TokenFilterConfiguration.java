package com.tutorial.springcloud.apigatewaygateway.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by huanghao on 2019/6/3 0003.
 *
 * @author huanghao
 */
@Configuration
public class TokenFilterConfiguration {

    // @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
