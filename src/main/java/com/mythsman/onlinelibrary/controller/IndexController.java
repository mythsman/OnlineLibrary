package com.mythsman.onlinelibrary.controller;

import com.mythsman.onlinelibrary.dao.ArticleDao;
import com.mythsman.onlinelibrary.model.Article;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by myths on 5/14/17.
 */
@Controller
public class IndexController {
    Logger logger= LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ArticleDao articleDao;

    @RequestMapping(path = {"index", ""}, method = {RequestMethod.GET})
    public String index() {
        return "redirect:/app";
    }

    @RequestMapping(path = {"/MP_verify_tH5a541PLbsArP3H.txt"}, method = {RequestMethod.GET})
	@ResponseBody
    public String verify() {
        return "tH5a541PLbsArP3H";
    }


    @RequestMapping(path = {"/download/{fid}"}, method = {RequestMethod.GET})
    public void download(@PathVariable("fid")String fid, HttpServletResponse httpServletResponse) {
        getFile(fid,httpServletResponse,"attachment");
    }

    @RequestMapping(path = {"/preview/{fid}"}, method = {RequestMethod.GET})
    public void preview(@PathVariable("fid")String fid, HttpServletResponse httpServletResponse) {
        getFile(fid,httpServletResponse,"inline");
    }

    private void getFile(String fid, HttpServletResponse httpServletResponse, String content){
        Article article=articleDao.selectByFid(Integer.parseInt(fid));
        String name="/home/ubuntu/uploads/"+article.getHash()+".pdf";

        logger.info(name+" has been previewed.");

        httpServletResponse.setContentType("application/oct-stream");
        FileInputStream fis = null;
        try {
            File file = new File(name);
            fis = new FileInputStream(file);
            httpServletResponse.setHeader("Content-Disposition", content+"; filename="+file.getName());
            IOUtils.copy(fis,httpServletResponse.getOutputStream());
            httpServletResponse.flushBuffer();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
