package com.tutorial.springcloud.apigatewayzuul.config;

import com.netflix.zuul.ZuulFilter;
import com.tutorial.springcloud.apigatewayzuul.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huanghao on 2019/5/22 0022.
 * @author huanghao
 */
@Configuration
public class FilterConfig {

    @Bean
    public ZuulFilter tokenFilter() {
        return new TokenFilter();
    }

}
