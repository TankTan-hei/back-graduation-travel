package com.travel.common.lang;

/**
 * Created on 2021/4/8.
 *
 * @author Zhouyong Tan
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return Result.succ(200,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result r=new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(int code,String msg,Object data){
        Result r=new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg){
        Result r=new Result();
        r.setCode(-1);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

}
