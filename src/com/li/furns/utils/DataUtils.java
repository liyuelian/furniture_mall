package com.li.furns.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author 李
 * @version 1.0
 */
public class DataUtils {
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            //将req.getParameterMap()的数据自动封装到furn对象中
            //底层使用反射将数据封装，前提是，表单提交的数据字段名称必须和Javabean的属性名一致
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //将字符串转成数字，否则返回默认值
    public static int parseInt(String str, int defaultVal) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println(str + " 格式不正确");
        }
        return defaultVal;

    }
}
