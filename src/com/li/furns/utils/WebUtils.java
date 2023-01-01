package com.li.furns.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李
 * @version 1.0
 */
public class WebUtils {
    /**
     * 判断一个请求是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
