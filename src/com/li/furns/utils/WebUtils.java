package com.li.furns.utils;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author 李
 * @version 1.0
 */
public class WebUtils {
    //定义一个文件上传的路径
    public static String FURN_IMG_DIRECTORY = "assets/images/product-image";

    public static String getYearMonthDay() {
        //如何得到当前的日期
        LocalDateTime ldt = LocalDateTime.now();
        int year = ldt.getYear();
        int monthValue = ldt.getMonthValue();
        int dayOfMonth = ldt.getDayOfMonth();
        String yearMonthDay = "/" + year + "/" + monthValue + "/" + dayOfMonth;
        return yearMonthDay;
    }

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
