package org.gyt.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 一级页面路由器
 * Created by y27chen on 2016/7/12.
 */
@RestController
public class PrimaryPageController {

    @RequestMapping("/")
    public ModelAndView getHomePage() {
        return new ModelAndView("index");
    }

    @RequestMapping("/index")
    public ModelAndView getHomePage2() {
        return new ModelAndView("index");
    }

    @RequestMapping("/home")
    public ModelAndView getHomePage3() {
        return new ModelAndView("index");
    }

    @RequestMapping("/bible")
    public ModelAndView biblePage() {
        return new ModelAndView("biblePage");
    }

    @RequestMapping("/believe")
    public ModelAndView christBelievePage() {
        return new ModelAndView("christBelievePage");
    }

    @RequestMapping("/contact")
    public ModelAndView contactPage() {
        return new ModelAndView("contactPage");
    }

    @RequestMapping("/financial")
    public ModelAndView financialPage() {
        return new ModelAndView("financialPage");
    }

    @RequestMapping("/group")
    public ModelAndView groupPage() {
        return new ModelAndView("groupPage");
    }

    @RequestMapping("/about")
    public ModelAndView introductionPage() {
        return new ModelAndView("introductionPage");
    }

    @RequestMapping("/wedding")
    public ModelAndView marriagePage() {
        return new ModelAndView("marriagePage");
    }

    @RequestMapping("/media")
    public ModelAndView mediaPage() {
        return new ModelAndView("mediaPage");
    }

    @RequestMapping("/newcomer")
    public ModelAndView newComerPage() {
        return new ModelAndView("newComerPage");
    }

    @RequestMapping("/pastor")
    public ModelAndView pastorPage() {
        return new ModelAndView("pastorPage");
    }

    @RequestMapping("/preach")
    public ModelAndView preachPage() {
        return new ModelAndView("preachPage");
    }

    @RequestMapping("/recommend")
    public ModelAndView recommendPage() {
        return new ModelAndView("recommendPage");
    }

    @RequestMapping("/report")
    public ModelAndView reportPage() {
        return new ModelAndView("reportPage");
    }

    @RequestMapping("/sunday")
    public ModelAndView sundaySchoolPage() {
        return new ModelAndView("sundaySchoolPage");
    }

    @RequestMapping("/testimony")
    public ModelAndView testimonyPage() {
        return new ModelAndView("testimonyPage");
    }

    @RequestMapping("/worship")
    public ModelAndView worshipPage() {
        return new ModelAndView("worshipPage");
    }
}
