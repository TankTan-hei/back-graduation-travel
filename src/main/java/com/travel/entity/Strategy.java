package com.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.sql.Date;
import java.time.LocalDate;
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
 * @since 2021-04-20
 */
@TableName("strategy")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Strategy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "stra_code", type = IdType.AUTO)
    private Integer straCode;

    private Integer uCode;

    private Data straDate;

    private Integer fabulous;

    private String straTxt;

    private String straImage;

    private String straTitle;


}
