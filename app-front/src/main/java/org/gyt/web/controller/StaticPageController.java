package org.gyt.web.controller;

import org.gyt.web.core.service.ArticleService;
import org.gyt.web.core.service.FellowshipService;
import org.gyt.web.core.service.SlidePictureService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.Message;
import org.gyt.web.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

/**
 * 静态页面路由器
 * Created by y27chen on 2016/7/12.
 */
@RestController
public class StaticPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticPageController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private SlidePictureService slidePictureService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("index");
        modelAndView.addObject("churchArticles", articleService.getChurchArticles());
        modelAndView.addObject("fellowshipArticles", articleService.getFellowshipArticles());
        modelAndView.addObject("imageGallery", slidePictureService.list());

        LOGGER.info("访问首页");

        return modelAndView;
    }

    @RequestMapping("/index")
    public ModelAndView getHomePage2() {
        return getHomePage();
    }

    @RequestMapping("/home")
    public ModelAndView getHomePage3() {
        return getHomePage();
    }

    /* 在线圣经 */
    @RequestMapping("/bible")
    public ModelAndView biblePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/biblePage");
        modelAndView.addObject("title", "在线圣经_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 基督信仰 */
    @RequestMapping("/believe")
    public ModelAndView christBelievePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/christBelievePage");
        modelAndView.addObject("title", "基督信仰_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 联系我们 */
    @RequestMapping("/contact")
    public ModelAndView contactPage(
            @RequestParam(required = false) boolean publishSuccess,
            @RequestParam(required = false) boolean publishFailed
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/contactPage");
        Message message = new Message();
        message.setType(MessageType.SUFFRAGE);
        modelAndView.addObject("title", "联系我们_基督教光音堂");
        modelAndView.addObject("message", message);
        modelAndView.addObject("fellowships", fellowshipService.getAll().stream().filter(fellowship ->
                fellowship.isEnable() &&
                        !fellowship.getName().equals("worship") &&
                        !fellowship.getName().equals("testimony") &&
                        !fellowship.getName().equals("report") &&
                        !fellowship.getName().equals("public") &&
                        !fellowship.getName().equals("suffrage") &&
                        !fellowship.getName().equals("recommend")
        ).collect(Collectors.toList()));
        modelAndView.addObject("publishSuccess", publishSuccess);
        modelAndView.addObject("publishFailed", publishFailed);
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 奉献捐赠 */
    @RequestMapping("/devotion")
    public ModelAndView devotionPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/devotionPage");
        modelAndView.addObject("title", "奉献捐赠_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 团契生活 */
    @RequestMapping("/fellowship")
    public ModelAndView groupPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/fellowshipPage");
        modelAndView.addObject("title", "团契生活_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 教堂简介 */
    @RequestMapping("/about")
    public ModelAndView introductionPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/introductionPage");
        modelAndView.addObject("title", "教堂简介_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 教堂婚礼 */
    @RequestMapping("/wedding")
    public ModelAndView marriagePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/weddingPage");
        modelAndView.addObject("title", "教堂婚礼_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 媒体资源 */
    @RequestMapping("/media")
    public ModelAndView mediaPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/mediaPage");
        modelAndView.addObject("title", "媒体资源_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 主任牧师 */
    @RequestMapping("/pastor")
    public ModelAndView pastorPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/pastorPage");
        modelAndView.addObject("title", "主任牧师_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 公益活动 */
    @RequestMapping("/public")
    public ModelAndView publicPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/publicPage");
        Fellowship fellowship = fellowshipService.get("public");
        modelAndView.addObject("title", "公益活动_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 好文推荐 */
    @RequestMapping("/recommend")
    public ModelAndView recommendPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/recommendPage");
        Fellowship fellowship = fellowshipService.get("recommend");
        modelAndView.addObject("title", "好文推荐_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 事工报告 */
    @RequestMapping("/report")
    public ModelAndView reportPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/reportPage");
        Fellowship fellowship = fellowshipService.get("report");
        modelAndView.addObject("title", "事工报告_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 主内服侍 */
    @RequestMapping("/service")
    public ModelAndView servicePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/servicePage");
        modelAndView.addObject("title", "主内服侍_基督教光音堂");
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 教会代祷 */
    @RequestMapping("/suffrage")
    public ModelAndView suffragePage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/suffragePage");
        Fellowship fellowship = fellowshipService.get("suffrage");
        modelAndView.addObject("title", "教会代祷_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 儿童主日学 */
    @RequestMapping("/sunday")
    public ModelAndView sundaySchoolPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/sundaySchoolPage");
        Fellowship fellowship = fellowshipService.get("sunday");
        modelAndView.addObject("title", "主日学_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 见证分享 */
    @RequestMapping("/testimony")
    public ModelAndView testimonyPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/testimonyPage");
        Fellowship fellowship = fellowshipService.get("testimony");
        modelAndView.addObject("title", "见证分享_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }

    /* 主日崇拜 */
    @RequestMapping("/worship")
    public ModelAndView worshipPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/worshipPage");
        Fellowship fellowship = fellowshipService.get("worship");
        modelAndView.addObject("title", "主日崇拜_基督教光音堂");
        modelAndView.addObject("items", fellowship.getArticles());
        LOGGER.info("访问静态页面 {}", modelAndView.getModel().get("title"));
        return modelAndView;
    }
}
