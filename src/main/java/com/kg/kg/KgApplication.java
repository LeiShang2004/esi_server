package com.kg.kg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.kg.kg.filter")
public class KgApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgApplication.class, args);
    }

}
