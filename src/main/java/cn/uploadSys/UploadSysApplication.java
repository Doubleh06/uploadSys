package cn.uploadSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.uploadSys.dao")
@EnableScheduling
public class UploadSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadSysApplication.class, args);
	}
}
