package com.tutorial.springcloud.apigatewaygateway.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huanghao on 2019/6/11 0011.
 *
 * @author huanghao
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        return "Fallback!\nfrom gateway";
    }

}
