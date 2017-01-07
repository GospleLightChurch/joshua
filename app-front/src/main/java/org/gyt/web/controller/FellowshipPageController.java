package org.gyt.web.controller;

import org.gyt.web.core.service.FellowshipService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.Article;
import org.gyt.web.model.Fellowship;
import org.gyt.web.repository.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * 团契页面路由器
 * Created by y27chen on 2016/7/12.
 */
@RestController
@RequestMapping("/fellowship")
public class FellowshipPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FellowshipPageController.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/{name}")
    public ModelAndView getFellowshipHomePage(@PathVariable String name, Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/fellowship/" + name);

        filterSpecialPage(modelAndView, name);

        Fellowship fellowship = fellowshipService.get(name);
        Page<Article> articles = articleRepository.findByFellowshipOrderByLastModifiedTimeDesc(pageable, fellowship);

        modelAndView.addObject("title", String.format("%s_基督教光音堂", fellowship.getDisplayName()));
        modelAndView.addObject("items", articles.getContent());
        paginationComponent.addPagination(modelAndView, articles, "/fellowship/" + name);

        LOGGER.info("访问团契 {}", fellowship.getDisplayName());

        return modelAndView;
    }

    private void filterSpecialPage(ModelAndView modelAndView, String name) {
        if (name.equalsIgnoreCase("worship")) {
            modelAndView.setViewName("staticPage/worshipPage");
        } else if (name.equalsIgnoreCase("testimony")) {
            modelAndView.setViewName("staticPage/testimonyPage");
        } else if (name.equalsIgnoreCase("report")) {
            modelAndView.setViewName("staticPage/reportPage");
        } else if (name.equalsIgnoreCase("public")) {
            modelAndView.setViewName("staticPage/publicPage");
        } else if (name.equalsIgnoreCase("suffrage")) {
            modelAndView.setViewName("staticPage/suffragePage");
        } else if (name.equalsIgnoreCase("recommend")) {
            modelAndView.setViewName("staticPage/recommendPage");
        } else if (name.equalsIgnoreCase("sunday")) {
            modelAndView.setViewName("staticPage/sundaySchoolPage");
        }
    }
}
