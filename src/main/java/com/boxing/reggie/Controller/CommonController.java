package com.boxing.reggie.Controller;

import com.boxing.reggie.common.R;

import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/12/11 22:11
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basepath;

    @RequestMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

//        使用原文件名
        String originalFilename =file.getOriginalFilename();


//          获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//        随机产生成文件名（uuid）
        String filename = UUID.randomUUID().toString()+suffix;

        File dir=new File(basepath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //创建文件夹
            dir.mkdirs();

        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basepath+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  R.success(filename) ;
    }
//文件的的下载

    /**
     *
     * @param name
     * @param Response
     */
    @GetMapping("/download")
    public void  download(String name, HttpServletResponse Response){

            //输入流，通过输入流读取文件内容

        FileInputStream inputStream=null;
        ServletOutputStream outputStream=null;


        try {
           inputStream =new FileInputStream(basepath+name);
            //输出流，通过输出流将文件协会浏览器，在浏览器展示图片
            outputStream = Response.getOutputStream();
            Response.setContentType("image/jpeg");
                int len=0;
             byte[] bytes = new byte[1024];
           while ((len = inputStream.read(bytes))!=-1){
            outputStream.write(bytes, 0, len);
            //刷新
            outputStream.flush();
           }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream!=null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }
}
