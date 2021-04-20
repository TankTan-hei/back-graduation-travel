package com.travel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zhouyong Tan
 * @since 2021-04-09
 */
@TableName("user")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    @TableId(value = "uCode",type = IdType.AUTO)
    private static final long serialVersionUID = 1L;

    private Integer uCode;

    private String uIma;

    private String uAcc;

    private String uPass;

    private String uSex;

    private Integer uLevel;

    private String uName;

    private String uInfo;

    private Integer uOld;

    private String uAdd;


}
