package com.mythsman.onlinelibrary.service;

import com.mythsman.onlinelibrary.dao.CourseDao;
import com.mythsman.onlinelibrary.model.Course;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myths on 5/15/17.
 */
@Component
public class CourseService implements InitializingBean{

    private Map<String,Map<String,List<String>>> courseMap;

    @Autowired
    private CourseDao courseDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Course> list=courseDao.selectCourseList();
        courseMap=new HashMap<>();
        for(Course course:list){
            if(!courseMap.containsKey(course.getSchoolName())){
                courseMap.put(course.getSchoolName(),new HashMap<>());
            }
            Map<String,List<String>> colleges=courseMap.get(course.getSchoolName());
            if(!colleges.containsKey(course.getCollegeName())){
                colleges.put(course.getCollegeName(),new ArrayList<>());
            }
            List<String> courses=colleges.get(course.getCollegeName());
            courses.add(course.getCourseName());
        }
    }

    public Map<String,Map<String,List<String>>> getCourseMap() {
        return courseMap;
    }

}
