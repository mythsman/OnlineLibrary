package com.mythsman.onlinelibrary.dao;

import com.mythsman.onlinelibrary.model.Article;
import com.mythsman.onlinelibrary.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by myths on 5/15/17.
 */
@Mapper
@Service
public interface UserDao {
    @Select({"insert into user(name,sex,province,city,country,avatar,openid) values(#{name},#{sex},#{province},#{city},#{country},#{avatar},#{openid})"})
    public void insert(
            @Param("name")String name,
            @Param("sex")int sex,
            @Param("province")String province,
            @Param("city")String city,
            @Param("country")String country,
            @Param("avatar")String avatar,
            @Param("openid")String openid
    );
    @Select({"select * from user where id=#{id}"})
    public User selectById(@Param("id")int id);


    @Select({"select * from user where openid=#{openid}"})
    public User selectByOpenid(@Param("openid")String openid);

}
