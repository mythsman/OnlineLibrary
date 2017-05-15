package com.mythsman.onlinelibrary.controller;

import com.alibaba.fastjson.JSON;
import com.mythsman.onlinelibrary.CourseComponent;
import com.mythsman.onlinelibrary.dao.ArticleDao;
import com.mythsman.onlinelibrary.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by myths on 5/14/17.
 */
@RestController
public class SearchController {

    @Autowired
    ArticleDao articleDao;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.POST})
    public String search(@RequestParam("school") String school, @RequestParam("college") String college, @RequestParam("course") String course) {
        List<Article> list=articleDao.selectByCourse(school,college,course);
        return JSON.toJSONString(list);
    }
}
