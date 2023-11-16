package com.boxing.reggie;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/19 17:55
 */
@Slf4j
//日志
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目运行成功");
    }
}
