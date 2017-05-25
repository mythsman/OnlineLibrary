package com.mythsman.onlinelibrary.dao;

import com.mythsman.onlinelibrary.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by myths on 5/15/17.
 */
@Mapper
@Service
public interface ArticleDao {
    @Select({"select * from `article` where school=#{school} and college=#{college} and course=#{course} and valid=1"})
    public List<Article> selectByCourse(@Param("school") String school, @Param("college") String college, @Param("course") String course);


    @Insert({"insert into `article`(uid,school,college,course,name,rawname,brief,hash) values(${uid},'${school}','${college}','${course}','${name}','${rawname}','${brief}','${hash}')"})
    public void insert(@Param("uid") int uid, @Param("school") String school, @Param("college") String college, @Param("course") String course, @Param("name") String name, @Param("rawname") String rawname, @Param("brief") String brief, @Param("hash") String hash);

    @Select({"select * from `article` where valid=0"})
    public List<Article> selectInvalid();

    @Update({"update `article` set valid=1 where fid=#{fid}"})
    public void verify(@Param("fid") int fid);

    @Select({"select * from `article` where fid=#{fid}"})
    public Article selectByFid(@Param("fid") int fid);

}
