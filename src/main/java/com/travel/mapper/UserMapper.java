package com.travel.mapper;

import com.travel.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zhouyong Tan
 * @since 2021-04-09
 */
/**
 * 在对应的mapper接口上继承BaseMapper类，即可完成所有的crud操作
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
