package com.travel.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2021/4/11.
 *
 * @author Zhouyong Tan
 */
@Data
public class LoginDto implements Serializable {
    private String u_code;
    private String u_acc;
    private String u_pass;
}
