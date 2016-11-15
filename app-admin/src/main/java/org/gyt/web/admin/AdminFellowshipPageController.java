package org.gyt.web.admin;

import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.FellowshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class AdminFellowshipPageController {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/fellowship")
    public ModelAndView tablePage() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-fellowship");
        modelAndView.addObject("subtitle", "所有团契");
        modelAndView.addObject("items", fellowshipRepository.findAllByOrderByName());
        return modelAndView;
    }

    @RequestMapping("/myfellowship")
    public ModelAndView myTablePage() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-fellowship");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("subtitle", "我的团契");

        modelAndView.addObject("items", fellowshipRepository.findByUser(user));
        return modelAndView;
    }

    @RequestMapping("/fellowship/{name}")
    public ModelAndView detailsPage(
            @PathVariable String name
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-fellowship-details");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipRepository.findOne(name);

        if (null == fellowship) {
            modelAndView.setViewName("404");
            modelAndView.addObject("message", String.format("找不到团契：%s", name));
        } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))
                || fellowship.getOwner().equals(user)
                || fellowship.getAdmins().contains(user)) {
            modelAndView.addObject("item", fellowship);
            modelAndView.addObject("subtitle", fellowship.getDisplayName());
        } else {
            modelAndView.setViewName("403");
            modelAndView.addObject("message", "您没有权限访问该团契");
        }

        return modelAndView;
    }
}
