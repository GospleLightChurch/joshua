package org.gyt.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置
 * Created by y27chen on 2016/7/12.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .contentTypeOptions().disable()
                .and()
                .authorizeRequests()
                /* 首页开放所有权限 */
                .antMatchers("/", "/index", "/home").permitAll()
                /* 默认静态页面开放所有权限 */
                .antMatchers("/about", "/pastor", "/contact", "/believe", "/newcomer", "/fellowship", "/service", "/bible", "/wedding", "/devotion").permitAll()
                /* 有新闻动态的静态页面开放所有权限 */
                .antMatchers("/worship", "/sunday", "/testimony", "/recommend", "/media", "/report", "/public", "/suffrage").permitAll()
                /* 登录注册页面开放所有权限 */
                .antMatchers("/login", "/logon", "/forget").permitAll()
                /* 静态资源开放所有权限 */
                .antMatchers("/assets/**", "/cover/**", "/gallery/**").permitAll()
                /* 文章页面和团契首页开放所有权限 */
                .antMatchers("/article/**", "/fellowship/**").permitAll()
                .and()

                .authorizeRequests()
                /* 调用后台API需要登录 */
                .antMatchers("/api/**").access("hasRole('ROLE_SEND_MESSAGE')")
                /* 后台访问需要具有后台访问权限 */
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN_ACCESS')")
                /* 静态页面管理需要具有静态页面管理权限 */
                .antMatchers("/admin/static/**").access("hasRole('ROLE_MANAGE_STATIC_PAGE')")
                /* 用户管理需要具有用户管理权限 */
                .antMatchers("/admin/user/**").access("hasRole('ROLE_MANAGE_USER_STATUS')")
                /* 发布消息需要具有发布消息权限 */
                .antMatchers("/message/publish").access("hasRole('ROLE_SEND_MESSAGE')")
                /* 消息管理需要具有消息管理权限 */
                .antMatchers("/message/read").access("hasRole('ROLE_MANAGE_MESSAGE')")
                .antMatchers("/message/unread").access("hasRole('ROLE_MANAGE_MESSAGE')")
                /* 查看个人信息需要已经登录 */
                .antMatchers("/info").access("hasRole('ROLE_SEND_MESSAGE')")
                /* 修改密码需要已经登录 */
                .antMatchers("/password").access("hasRole('ROLE_SEND_MESSAGE')")
                .and()

                /* 其余所有请求都需要已经认证 */
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()

                /* 忽略特定CSRF验证 */
                .csrf()
                .ignoringAntMatchers("/forget", "/login", "/logon")
                .and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .and()

                .logout()
                .and()

                .exceptionHandling()
                .accessDeniedPage("/403");
    }
}
