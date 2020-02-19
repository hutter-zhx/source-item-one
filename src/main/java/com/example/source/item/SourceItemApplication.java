package com.example.source.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.source.item.mapper")
@EnableTransactionManagement
public class SourceItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourceItemApplication.class, args);
    }

}
