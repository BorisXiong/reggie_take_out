package com.boxing.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.boxing.reggie.Entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/28 1:59
 */

/**
 *自定义元数据处理器
 *
 */
@Component
@Slf4j
public class MyMetaObjectHandle implements MetaObjectHandler {
    /**
     *插入操作时候自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("进入insertFill...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        log.info("updateTime{}",LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.GetCurrentId());
        metaObject.setValue("updateUser", BaseContext.GetCurrentId());
}
    /**
     * 更改操作时候自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        long id = Thread.currentThread().getId();
        log.info("线程id为{}",id);

        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.GetCurrentId());
    }
}
