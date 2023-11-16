package com.boxing.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/26 0:28
 *
 * 分页插件
 */


@Configuration
public class MyBatisPlusConfig  {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

            MybatisPlusInterceptor mybatisPlusInterceptor =new MybatisPlusInterceptor();
            mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
           return mybatisPlusInterceptor;
    }
}
