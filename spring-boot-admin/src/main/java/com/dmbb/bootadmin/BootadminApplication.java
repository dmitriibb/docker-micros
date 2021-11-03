package com.dmbb.bootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class BootadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootadminApplication.class, args);
	}

}
