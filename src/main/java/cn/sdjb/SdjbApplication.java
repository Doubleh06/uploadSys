package cn.sdjb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.sdjb.dao")
@EnableScheduling
public class SdjbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdjbApplication.class, args);
	}
}
