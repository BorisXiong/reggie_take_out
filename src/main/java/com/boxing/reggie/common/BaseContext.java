package com.boxing.reggie.common;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/28 2:27
 */

/**
 * 基于ThreadLOcal封装工具类，用户保存和获取当前登录用户id
 */

public class BaseContext {

    private  static  ThreadLocal<Long> ThreadLocal =new  ThreadLocal<>();

    public static   void setCurrentId(Long id){
        ThreadLocal.set(id);
    }

    public  static  Long GetCurrentId(){
        return  ThreadLocal.get();
    }


}
