package com.store.demo;

import com.sug.core.platform.sms.aliyun.AliyunSmsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.store",
		"com.sug.core.platform.web",
		"com.sug.core.rest",
		"com.sug.core.platform.sms.aliyun"

},excludeFilters={
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=AliyunSmsService.class)})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
