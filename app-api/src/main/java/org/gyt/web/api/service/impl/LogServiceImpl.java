package org.gyt.web.api.service.impl;

import org.gyt.web.api.service.LogService;
import org.gyt.web.api.utils.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private NetworkUtils networkUtils;

    @Override
    public void debug(HttpServletRequest request, String content) {
        debug(getHttpLogFormat(request) + content);
    }

    @Override
    public void debug(HttpServletRequest request, String format, String... param) {
        debug(getHttpLogFormat(request) + String.format(format, param));
    }

    @Override
    public void info(HttpServletRequest request, String content) {
        info(getHttpLogFormat(request) + content);
    }

    @Override
    public void info(HttpServletRequest request, String format, String... param) {
        info(getHttpLogFormat(request) + String.format(format, param));
    }

    @Override
    public void warn(HttpServletRequest request, String content) {
        warn(getHttpLogFormat(request) + content);
    }

    @Override
    public void warn(HttpServletRequest request, String format, String... param) {
        warn(getHttpLogFormat(request) + String.format(format, param));
    }

    @Override
    public void error(HttpServletRequest request, String content) {
        error(getHttpLogFormat(request) + content);
    }

    @Override
    public void error(HttpServletRequest request, String format, String... param) {
        error(getHttpLogFormat(request) + String.format(format, param));
    }

    @Override
    public void debug(String content) {
        log(content, "debug");
    }

    @Override
    public void debug(String format, String... param) {
        log(String.format(format, param), "debug");
    }

    @Override
    public void info(String content) {
        log(content, "info");
    }

    @Override
    public void info(String format, String... param) {
        log(String.format(format, param), "info");
    }

    @Override
    public void warn(String content) {
        log(content, "warn");
    }

    @Override
    public void warn(String format, String... param) {
        log(String.format(format, param), "warn");
    }

    @Override
    public void error(String content) {
        log(content, "error");
    }

    @Override
    public void error(String format, String... param) {
        log(String.format(format, param), "error");
    }

    private String getHttpLogFormat(HttpServletRequest request) {
        String username = request.getRemoteUser() == null ? "匿名" : request.getRemoteUser();
        String address = networkUtils.getRemoteIpAddress(request);
        String url = request.getRequestURL().toString();
        return String.format("[%s - %s - %s]", username, address, url);
    }

    private void log(String content, String level) {
        if (level.equalsIgnoreCase("debug")) {
            LOGGER.debug(content);
        } else if (level.equalsIgnoreCase("warn")) {
            LOGGER.warn(content);
        } else if (level.equalsIgnoreCase("error")) {
            LOGGER.error(content);
        } else {
            LOGGER.info(content);
        }
    }
}
