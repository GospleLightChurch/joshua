package org.gyt.web.api.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志服务，提供以下接口
 * - 打印网站访问日志
 * - 打印普通日志
 * Created by Administrator on 2016/10/24.
 */
public interface LogService {

    void debug(HttpServletRequest request, String content);

    void debug(HttpServletRequest request, String format, String... param);

    void info(HttpServletRequest request, String content);

    void info(HttpServletRequest request, String format, String... param);

    void warn(HttpServletRequest request, String content);

    void warn(HttpServletRequest request, String format, String... param);

    void error(HttpServletRequest request, String content);

    void error(HttpServletRequest request, String format, String... param);

    void debug(String content);

    void debug(String format, String... param);

    void info(String content);

    void info(String format, String... param);

    void warn(String content);

    void warn(String format, String... param);

    void error(String content);

    void error(String format, String... param);
}
