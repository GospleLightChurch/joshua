package org.gyt.web.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具
 * - 获取真实IP地址
 * Created by Administrator on 2016/10/24.
 */
@Component
public class NetworkUtils {

    public String getRemoteIpAddress(HttpServletRequest request) {
        String address = request.getHeader("X-Real-IP");

        if (!StringUtils.isBlank(address) && !"unknown".equalsIgnoreCase(address)) {
            return address;
        }

        address = request.getHeader("X-Forwarded-For");

        if (!StringUtils.isBlank(address) && !"unknown".equalsIgnoreCase(address)) {
            int index = address.indexOf(',');
            if (index != -1) {
                return address.substring(0, index);
            } else {
                return address;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
