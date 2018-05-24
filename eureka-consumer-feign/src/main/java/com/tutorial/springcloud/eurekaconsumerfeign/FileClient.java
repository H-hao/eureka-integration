package com.tutorial.springcloud.eurekaconsumerfeign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

// 引入第三方 feign 对表单提交的依赖，对 FeignClient 添加 form 编码
/**
 * @author Administrator
 * @date 2018/5/17
 */
@FeignClient(value = "eureka-producer", configuration = FileClient.MultipartSupportConfig.class)
public interface FileClient {

	@PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	String file(@RequestPart(value = "file") MultipartFile file);

	@Configuration
	class MultipartSupportConfig {
		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}
