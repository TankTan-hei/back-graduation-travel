package com.travel.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.dialect.console.ConsoleLog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.travel.UploadImages;
import com.travel.common.dto.LoginDto;
import com.travel.common.lang.Result;
import com.travel.entity.ScenicPoint;
import com.travel.entity.User;
import com.travel.mapper.UserMapper;
import com.travel.service.UserService;
import com.travel.util.JwtUtils;
import org.apache.ibatis.annotations.Select;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhouyong Tan
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    /**
     * 注册
     * @param response
     * @return
     */
    @PostMapping("/register")
    public Object register(@RequestBody JSONObject jsonObject,
                           HttpServletResponse response) {
        System.out.println(jsonObject);
        String u_acc=jsonObject.get("u_acc").toString();
        String u_pass=jsonObject.get("u_pass").toString();
        String u_sex=jsonObject.get("u_sex").toString();
        User user=new User();
        user.setUAcc(u_acc);
        if(userService.getOne(new QueryWrapper<User>().eq("u_acc",user.getUAcc()))==null){
            String pass=SecureUtil.md5(u_pass);
            user.setUPass(pass);
            user.setUSex(u_sex);
            System.out.println(user);
            userService.save(user);
            User newuser = userService.getOne(new QueryWrapper<User>().eq("u_acc",u_acc));
            String jwt = jwtUtils.generateToken(newuser.getUCode());
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return Result.succ(user);
        }
        else{
            return Result.fail("用户已存在");
        }
    }

    /**
     *登录
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody JSONObject jsonObject,
                        HttpServletResponse response) {
        String u_acc=jsonObject.get("u_acc").toString();
        String u_pass=jsonObject.get("u_pass").toString();
        User user = userService.getOne(new QueryWrapper<User>().eq("u_acc",u_acc));
        System.out.println(u_pass);
        String pass=SecureUtil.md5(u_pass);
        System.out.println(pass);
        Assert.notNull(user, "用户不存在");
        if(!user.getUPass().equals(pass)){
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getUCode());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(user);
    }

    /**
     * 更新用户密码
     */
    @PostMapping("/UpdateInfo")
    public Object UpdateInfo(@RequestParam("u_acc") String u_acc,
                             @RequestParam("u_pass") String u_pass
                             ){
        User user=new User();
        user.setUAcc(u_acc);
        user.setUPass(SecureUtil.md5(u_pass));
        if(userService.update(user, new QueryWrapper<User>().eq("u_acc",u_acc))){
            return Result.succ("修改密码成功");
        }else{
            return Result.fail("修改未成功");
        }
    }



    /**
     *退出
     */
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    /**
     *上传头像
     */


    /**
     *更改用户个人信息
     */

    @CrossOrigin
    @PostMapping("/editusers")
    public Result editusers(@RequestBody JSONObject jsonObject,
                           HttpServletResponse response) {
        String u_acc=jsonObject.get("u_acc").toString();
        String u_ima=jsonObject.get("u_ima").toString();
        String u_add=jsonObject.get("u_add").toString();
        String u_name=jsonObject.get("u_name").toString();
        User user=new User();
        user.setUIma(u_ima);
        user.setUName(u_name);
        user.setUAdd(u_add);
        if(userService.update(user, new QueryWrapper<User>().eq("u_acc",u_acc))){
            return Result.succ("成功");
        }else{
            return Result.fail("失败");
        }
    }

    /**
     *查询单个用户
     */
    @CrossOrigin
    @PostMapping("/getusers")
    public Result getusers(@RequestBody JSONObject jsonObject,
                        HttpServletResponse response) {
        String u_acc=jsonObject.get("u_acc").toString();
        User user = userService.getOne(new QueryWrapper<User>().eq("u_acc",u_acc));
        Assert.notNull(user, "用户不存在");
        String jwt = jwtUtils.generateToken(user.getUCode());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(user);
    }

    /**
     * 增加或修改用户头像
     */
    @RequestMapping("/UserpictureUpload")
    public  Result UserpictureUpload(
            @RequestParam("file") MultipartFile file){
        System.out.println(file);
        return Result.succ(UploadImages.upload(file));
    }
}

