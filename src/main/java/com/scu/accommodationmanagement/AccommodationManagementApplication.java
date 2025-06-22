package com.scu.accommodationmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scu.accommodationmanagement.mapper")
public class AccommodationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccommodationManagementApplication.class, args);
    }

}
