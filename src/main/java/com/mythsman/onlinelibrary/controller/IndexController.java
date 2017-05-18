package com.mythsman.onlinelibrary.controller;

import com.mythsman.onlinelibrary.component.CourseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by myths on 5/14/17.
 */
@Controller
public class IndexController {

    @Autowired
    CourseComponent courseComponent;

    @RequestMapping(path = {"/tab1","/"},method = {RequestMethod.GET})
    public String tab1(Model model){
        return "tab1";
    }

    @RequestMapping(path = {"/tab2"},method = {RequestMethod.GET})
    public String tab2(Model model){
        Map<String,Map<String,List<String>>> schools=courseComponent.getCourseMap();
        model.addAttribute("courseMap",schools);
        return "tab2";
    }

    @RequestMapping(path = {"/tab3"},method = {RequestMethod.GET})
    public String tab3(Model model){
        return "tab3";
    }
}
