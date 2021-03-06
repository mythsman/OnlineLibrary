package com.mythsman.onlinelibrary.controller;

import com.alibaba.fastjson.JSON;
import com.mythsman.onlinelibrary.component.UserComponent;
import com.mythsman.onlinelibrary.dao.ArticleDao;
import com.mythsman.onlinelibrary.model.Article;
import com.mythsman.onlinelibrary.service.CourseService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    UserComponent userComponent;

    @Value("${wechat.prefix}")
    String prefix;

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

    @RequestMapping(path = {"/detail/{fid}"}, method = {RequestMethod.GET})
    public String detail(Model model, @PathVariable("fid")String fid) {
        Article article=articleDao.selectByFid(Integer.parseInt(fid));
        model.addAttribute("article",article);
        return "detail";
    }

    @RequestMapping(path = {"/favourite"}, method = {RequestMethod.GET})
    public String favourite() {
        return "favourite";
    }

    @RequestMapping(path = {"/upload"}, method = {RequestMethod.GET})
    public String upload(Model model) {
        Map<String,Map<String,List<String>>> schools= courseService.getCourseMap();
        model.addAttribute("courseMap",schools);
        return "upload";
    }

    @RequestMapping(path = {"/upload"}, method = {RequestMethod.POST},params = {"school","college","course","title","brief"})
    public String getFile(@RequestParam("school")String school, @RequestParam("college")String college, @RequestParam("course")String course,@RequestParam("title")String title,@RequestParam("brief")String brief, @RequestParam("file")MultipartFile multipartFile) {
        String uuid32=UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        articleDao.insert(userComponent.getUser().getId(),school,college,course,title,multipartFile.getOriginalFilename(),brief,uuid32);
        try {
            File file=new File(prefix+uuid32);
            multipartFile.transferTo(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/app/tab3";
    }

    @RequestMapping(path = {"/about"}, method = {RequestMethod.GET})
    public String about() {
        return "about";
    }
}
