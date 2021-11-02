package com.dmbb.springappa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@EnableAspectJAutoProxy
public class SpringAppAApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAppAApplication.class, args);
	}

}
