package com.travel.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.lang.Result;
import com.travel.entity.ScenicPoint;
import com.travel.entity.Strategy;
import com.travel.mapper.ScenicPointMapper;
import com.travel.mapper.StrategyMapper;
import com.travel.service.ScenicPointService;
import com.travel.service.StrategyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhouyong Tan
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    StrategyMapper strategyMapper;

    @Autowired
    StrategyService strategyService;

    /**
     * 增加攻略
     */
    @PostMapping("/updateStrategy")
    public Result updateStrategy(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        String  stra_image= (String) jsonObject.get("stra_image");
        String  stra_title= (String) jsonObject.get("stra_title");
        String  stra_txt= (String) jsonObject.get("stra_txt");
        int  u_code=Integer.parseInt(jsonObject.get("u_code").toString());
        Strategy strategy=new Strategy();
        strategy.setUCode(u_code);
        strategy.setStraTxt(stra_txt);
        strategy.setStraTitle(stra_title);
        strategy.setStraImage(stra_image);
        if(strategyService.saveOrUpdate(strategy)){
            return Result.succ("增加或修改攻略成功");
        }else{
            return Result.fail("增加或修改攻略失败");
        }
    }

    /**
     * 所有攻略
     */
    @GetMapping("/getStrategy")
    public Result getStrategy(){
        IPage<Strategy> page = new Page<>(1, 10);
        return Result.succ(strategyService.page(page,null));
    }

    /**
     * 根据straCode获取攻略
     */
    @PostMapping("/getOneStrategy")
    public Result getOneStrategy(@RequestBody JSONObject jsonObject){
        int stra_code=Integer.parseInt(jsonObject.get("stra_code").toString());
        System.out.println(stra_code);
        Strategy strategy=strategyService.getOne(new QueryWrapper<Strategy>().eq("stra_code",stra_code));
        return Result.succ(strategy);
    }

}
