package com.mythsman.onlinelibrary.dao;

import com.mythsman.onlinelibrary.model.Article;
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
public interface ArticleDao {
    @Select({"select * from `article` where school=#{school} and college=#{college} and course=#{course}"})
    public List<Article> selectByCourse(@Param("school") String school, @Param("college") String college,@Param("course")String course);
}
