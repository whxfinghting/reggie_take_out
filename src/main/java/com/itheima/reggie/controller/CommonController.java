package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.MultiPartName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author whx
 * @create 2022-07-20 10:05
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private  String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        //获取原始文件名
       String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用uuid重新生成文件名
        String fileName = UUID.randomUUID().toString()+substring;

        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }


    @GetMapping("/download")
    public void download(HttpServletResponse response,String name){

        FileInputStream fileInputStream=null;
        ServletOutputStream outputStream=null;
        try {
            //创建输入流获取文件
            fileInputStream  = new FileInputStream(new File(basePath + name));
            //
            outputStream = response.getOutputStream();

            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream!=null){
                    fileInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStream!=null){
                    outputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
