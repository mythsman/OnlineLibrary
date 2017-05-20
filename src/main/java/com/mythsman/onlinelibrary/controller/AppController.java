package com.mythsman.onlinelibrary.controller;

import com.alibaba.fastjson.JSON;
import com.mythsman.onlinelibrary.dao.ArticleDao;
import com.mythsman.onlinelibrary.model.Article;
import com.mythsman.onlinelibrary.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by myths on 5/14/17.
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    CourseService courseService;

    @Autowired
    ArticleDao articleDao;

    @RequestMapping(path = {"/tab1",""},method = {RequestMethod.GET})
    public String tab1(Model model){
        return "tab1";
    }

    @RequestMapping(path = {"/tab2"},method = {RequestMethod.GET})
    public String tab2(Model model){
        Map<String,Map<String,List<String>>> schools= courseService.getCourseMap();
        model.addAttribute("courseMap",schools);
        return "tab2";
    }

    @RequestMapping(path = {"/tab3"},method = {RequestMethod.GET})
    public String tab3(Model model){
        return "tab3";
    }

    @RequestMapping(path = {"/search"}, method = {RequestMethod.POST})
    @ResponseBody
    public String search(@RequestParam("school") String school, @RequestParam("college") String college, @RequestParam("course") String course) {
        List<Article> list=articleDao.selectByCourse(school,college,course);
        return JSON.toJSONString(list);
    }
}
