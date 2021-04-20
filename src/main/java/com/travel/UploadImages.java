package com.travel;

import com.travel.common.lang.Result;
import com.travel.util.IDUtils;
import com.travel.util.SftpUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 2021/4/7.
 *图片上传
 * @author Zhouyong Tan
 */
public class UploadImages {
    public static String upload(MultipartFile uploadFile){
        String filePath = null;
        try {
            System.out.println("进入");
            System.out.println(uploadFile);
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            //1.3生成文件在服务器端存储的子目录
            filePath = new DateTime().toString("/yyyy/MM/dd");
            System.out.println(filePath);
            System.out.println(newName);
            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();
            //之前使用的FTPTils工具类，但是我部署的浏览器不支持连接，就改成了SFtpUtil
            //3.2调用SFtpUtil工具类进行上传
            boolean result = SftpUtils.uploadBySftp("42.192.86.206","ftpuser","Tzy1074567974.",22,"/home/ftpuser", filePath, newName, input);
            System.out.println(result);
            filePath = "http://42.192.86.206" + filePath + "/" + newName;
            System.out.println(filePath);
            if (result) {
                System.out.println(result);
            } else {
            }
        } catch (IOException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
