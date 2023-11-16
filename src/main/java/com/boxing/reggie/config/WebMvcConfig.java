package com.boxing.reggie.config;

import com.boxing.reggie.Entity.Employee;
import com.boxing.reggie.common.JacksonObjectMapper;
import com.boxing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/19 18:37
 */

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
       log.info("开始资源映射");
       registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
       registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
//        设置对象转换器，底层使用jackson将java对象转化为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        converters.add(0,messageConverter);
    }


}
