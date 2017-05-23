package com.mythsman.onlinelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by myths on 5/14/17.
 */
@Controller
public class IndexController {

    @RequestMapping(path = {"index", ""}, method = {RequestMethod.GET})
    public String index() {
        return "redirect:/app";
    }

    @RequestMapping(path = {"/MP_verify_tH5a541PLbsArP3H.txt"}, method = {RequestMethod.GET})
	@ResponseBody
    public String verify() {
        return "tH5a541PLbsArP3H";
    }

}
