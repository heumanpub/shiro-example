package com.heuman.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by heuman on 2018/3/22.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    @RequiresRoles(value = {"user,admin"}, logical = Logical.OR)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

}
