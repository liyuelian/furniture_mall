package com.li.furns.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 业务servlet的共同父类
 * BasicServlet 是供子类去继承的，不需要在web.xml中配置
 * 使用模板模式+反射+动态绑定===>简化了多个if-else的语句
 *
 * @author 李
 * @version 1.0
 */
public abstract class BasicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决接收到的数据中文乱码问题
        req.setCharacterEncoding("utf-8");

        //获取提交表单的隐藏域元素的值
        //如果我们使用模板模式+反射+动态绑定，要满足action的值要和方法名一致
        String action = req.getParameter("action");

        //使用反射，获取到当前对象的方法
        //1.this就是请求的业务Servlet，即运行类型
        //2.declaredMethod 方法对象就是当前请求的业务servlet对应的action名称的方法
        try {
            /**
             * public Method getDeclaredMethod(){}
             * 该方法返回一个Method对象，它反射此Class对象所表示的类或接口的指定已声明方法。
             * 参数：此方法接受两个参数：
             * -方法名称，这是要获取的方法。
             * -参数类型 这是指定的方法的参数类型的数组。
             * 返回值：此方法以 Method 对象的形式返回此类的指定方法。
             */
            Method declaredMethod =
                    this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println("declaredMethod=>" + declaredMethod);
            //使用方法对象进行反射调用
            //public Object invoke(Object obj, Object... args){}
            declaredMethod.invoke(this, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //在BasicServlet中增加doGet请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
