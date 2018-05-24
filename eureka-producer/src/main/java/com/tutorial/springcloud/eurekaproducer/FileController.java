package com.tutorial.springcloud.eurekaproducer;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/5/17
 */
@RestController
public class FileController {

	@PostMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String add(@RequestPart("file")MultipartFile file) {
		String x = null;
		try {
			x = IOUtils.toString(file.getInputStream());
			System.out.println(x);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getName() + " - " + x;
	}

}
