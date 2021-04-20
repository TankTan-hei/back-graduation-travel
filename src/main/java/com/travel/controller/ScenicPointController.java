package com.travel.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.travel.UploadImages;
import com.travel.common.lang.Result;
import com.travel.entity.ScenicPoint;
import com.travel.entity.User;
import com.travel.mapper.ScenicPointMapper;
import com.travel.service.ScenicPointService;
import com.travel.util.IDUtils;
import com.travel.util.SftpUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.Text;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhouyong Tan
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/scenic")
public class ScenicPointController {


    @Autowired
    ScenicPointService scenicPointService;

    @Autowired
    ScenicPointMapper scenicPointMapper;

    /**
     * 查看所有景点
     */
    @PostMapping("/scenicList")
    public Result scenicList(){
        IPage<ScenicPoint> page = new Page<>(1, 10);
        return Result.succ(scenicPointService.page(page,null));
    }

    /**
     * 增加景点
     */
    @PostMapping("/addScenic")
    public Result addScenic(@RequestBody ScenicPoint scenicPoint, HttpServletResponse response){
        if(scenicPointService.save(scenicPoint)){
            return Result.succ("增加景点成功");
        }else{
            return Result.fail("增加景点失败");
        }
    }

    /**
     * 增加或修改景点
     */
    @PostMapping("/updateScenic")
    public Result updateScenic(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        int  s_code=Integer.parseInt(jsonObject.get("s_code").toString());
        String  s_name= (String) jsonObject.get("s_name");
        int  s_level=Integer.parseInt(jsonObject.get("s_level").toString());
        String  s_address= (String) jsonObject.get("s_address");
        String  s_info= (String) jsonObject.get("s_info ");
        Double  s_lng= Double.parseDouble(jsonObject.get("s_lng").toString());
        Double  s_lat= Double.parseDouble(jsonObject.get("s_lat").toString());
        ScenicPoint scenicPoint=new ScenicPoint();
        scenicPoint.setSCode(s_code);
        scenicPoint.setSAddress(s_address);
        scenicPoint.setSInfo(s_info);
        scenicPoint.setSLevel(s_level);
        scenicPoint.setSLng(s_lng);
        scenicPoint.setSLat(s_lat);
        scenicPoint.setSName(s_name);
        if(scenicPointService.saveOrUpdate(scenicPoint)){
            return Result.succ("增加或修改景点成功");
        }else{
            return Result.fail("增加或修改景点失败");
        }
    }

    /**
     * 批量增加或修改景点
     */
    @PostMapping("/batchUpdateScenic")
    public Result batchUpdateScenic(@RequestBody List<ScenicPoint> scenicPointList, HttpServletResponse response){
        if(scenicPointService.saveOrUpdateBatch(scenicPointList)){
            return Result.succ("批量增加或修改景点成功");
        }else{
            return Result.fail("批量增加或修改景点失败");
        }
    }

    /**
     * 删除景点
     */
    @PostMapping("/deleteScenic")
    public Result deleteScenic(@RequestParam("s_code") int s_code, HttpServletResponse response){
        int result=scenicPointMapper.deleteById(s_code);
        if(result==1){
            return Result.succ(result);
        }else{
            return Result.fail("删除失败");
        }
    }

    /**
     * 批量删除景点(前端传递id的text)
     */
    @PostMapping("/batchDeleteScenic")
    public Result batchDeleteScenic(
           // @RequestParam("id") String string,
           @RequestBody JSONObject jsonObject){
        List multis=jsonObject.getJSONArray("multis");
//        String strList[] = string.split(",");
//        List numList = new ArrayList();
//        for (String str: strList) {
//            numList.add(Integer.parseInt(str));
//        }
        if(scenicPointService.removeByIds(multis)){
            return Result.succ("批量删除景点成功");
        }else{
            return Result.fail("批量删除景点失败");
        }
    }

    /**
     * 增加景点图片
     */
    @RequestMapping("/pictureUpload")
    public  Result pictureUpload(
            @RequestParam("s_ima") MultipartFile uploadFile){
        return Result.succ(UploadImages.upload(uploadFile));
    }

    /**
     *高级搜索(等级、分类、价格区间)
     */
    @RequestMapping("/search")
    public  Result search(
            @RequestBody JSONObject jsonObject){
        int  s_level=Integer.parseInt(jsonObject.get("s_level").toString());
        int  cate_code=Integer.parseInt(jsonObject.get("cate_code").toString());
        int  frontPrice=Integer.parseInt(jsonObject.get("frontPrice").toString());
        int  backPrice=Integer.parseInt(jsonObject.get("backPrice").toString());
        QueryWrapper<ScenicPoint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cate_code",cate_code);
        queryWrapper.eq("s_level",s_level);
        System.out.println(frontPrice);
        queryWrapper.between("s_price",frontPrice,backPrice);
        List<ScenicPoint> list = scenicPointMapper.selectList(queryWrapper);
        return Result.succ(list);
    }

    /**
     *模糊搜索（景点名）
     */
    @RequestMapping("/vagueSearch")
    public  Result vagueSearch(
            @RequestBody JSONObject jsonObject){
        String  s_name= (String) jsonObject.get("s_name");
        System.out.println(s_name);
        QueryWrapper<ScenicPoint> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("s_name",s_name);
        List<ScenicPoint> list = scenicPointMapper.selectList(queryWrapper);
        return Result.succ(list);
    }



}
