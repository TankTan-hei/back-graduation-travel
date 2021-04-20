package com.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * @since 2021-04-12
 */
@TableName("scenic_point")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScenicPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "s_code", type = IdType.AUTO)
    private Integer sCode;

    private Integer cateCode;

    private String sName;

    private String sAddress;

    private Double sLng;

    private Double sLat;

    private String sInfo;

    private String sImg;

    private Integer sLevel;

    private String sCategory;

    private Float sPrice;

    private Integer sScore;


}
