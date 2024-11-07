package com.simulator.picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PicPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicPayApplication.class, args);
	}

}
