package cn.nlf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.nlf.dao")
@EnableScheduling
public class NlfApplication {

	public static void main(String[] args) {
		SpringApplication.run(NlfApplication.class, args);
	}
}
