package com.mythsman.onlinelibrary.dao;

import com.mythsman.onlinelibrary.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by myths on 5/15/17.
 */
@Mapper
@Service
public interface CourseDao {
    @Select({"select * from `course`;"})
    public List<Course> selectCourseList();
}
