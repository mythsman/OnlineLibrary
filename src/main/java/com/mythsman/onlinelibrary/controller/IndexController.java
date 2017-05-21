package com.mythsman.onlinelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by myths on 5/14/17.
 */
@Controller
public class IndexController {

    @RequestMapping(path = {"index", ""}, method = {RequestMethod.GET})
    public String index() {
        return "redirect:/app";
    }


}
