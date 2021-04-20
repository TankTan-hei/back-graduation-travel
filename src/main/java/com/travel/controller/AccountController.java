package com.travel.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.travel.common.dto.LoginDto;
import com.travel.common.lang.Result;
import com.travel.entity.User;
import com.travel.service.UserService;
import com.travel.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created on 2021/4/11.
 *
 * @author Zhouyong Tan
 */
@RestController
@RequestMapping("/AccountController")
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;


    /**
     * 管理员端
     * 查看用户所有信息
     */
    @GetMapping("/userList")
    public Result userList(){
        return Result.succ(userService.list());
    }

    // 退出
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }


    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("u_code", loginDto.getU_code()));
        Assert.notNull(user, "用户不存在");
        if(!user.getUPass().equals(SecureUtil.md5(loginDto.getU_pass()))) {
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getUCode());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getUCode())
                .put("u_name", user.getUName())
                .put("u_ima", user.getUIma())
                .map()
        );
    }


}
