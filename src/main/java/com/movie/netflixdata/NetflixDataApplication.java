package com.movie.netflixdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class NetflixDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixDataApplication.class, args);
	}

}
