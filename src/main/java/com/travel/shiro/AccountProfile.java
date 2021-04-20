package com.travel.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2021/4/8.
 *
 * @author Zhouyong Tan
 */
@Data
public class AccountProfile implements Serializable {
    private Long u_code;
    private String u_acc;
    private String u_name;
    private String u_ima;
}

