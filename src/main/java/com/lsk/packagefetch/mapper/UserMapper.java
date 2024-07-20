package com.lsk.packagefetch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsk.packagefetch.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
