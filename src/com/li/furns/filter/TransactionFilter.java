package com.li.furns.filter;

import com.li.furns.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

/**
 * 管理事务
 *
 * @author 李
 * @version 1.0
 */
public class TransactionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            //先放行
            chain.doFilter(request, response);
            //统一提交
            JDBCUtilsByDruid.commit();

        } catch (Exception e) {
            //只有在try{}中出现了异常，才会进行catch{}
            //这里想要捕获异常，前提是底层的代码没有将抛出的异常捕获
            JDBCUtilsByDruid.rollback();//回滚
            e.printStackTrace();
        }
    }
}
