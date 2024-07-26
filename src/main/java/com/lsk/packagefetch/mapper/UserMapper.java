package com.lsk.packagefetch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsk.packagefetch.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set finished_order_count=finished_order_count + 1 where id=#{id}")
    void increaseFinishedOrderCount(@Param("id") Integer id);
    @Update("update user set published_order_count=published_order_count+1 where id=#{id}")
    void increasePublishedOrderCount(@Param("id") Integer id);
}
