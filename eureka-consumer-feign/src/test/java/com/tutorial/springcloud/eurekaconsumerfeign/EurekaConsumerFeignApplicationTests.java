package com.tutorial.springcloud.eurekaconsumerfeign;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaConsumerFeignApplicationTests {

	@Autowired
	FileClient fileClient;

	@Test
	public void fileUpload() {
		File file = new File("E:\\WorkSpace\\IDEAWork\\tutorial\\springcloud\\eureka\\eureka-consumer-feign\\src\\test\\resources\\upload.txt");
		DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
				MediaType.TEXT_PLAIN_VALUE, true, file.getName());

		try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
			IOUtils.copy(input, os);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid file: " + e, e);
		}
		MultipartFile multi = new CommonsMultipartFile(fileItem);
		System.out.println(fileClient.file(multi));
	}

}
