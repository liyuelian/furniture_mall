package com.li.furns.web;

import com.li.furns.entity.Furn;
import com.li.furns.entity.Page;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;
import com.li.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FurnServlet extends BasicServlet {

    private FurnService furnService = new FurnServiceImpl();

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Furn> furns = furnService.queryFurns();
        //将furns集合放入到request域中
        req.setAttribute("furns", furns);
        //请求转发
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }

    /**
     * 处理添加家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //获取家居信息
//        String name = req.getParameter("name");
//        String maker = req.getParameter("maker");
//        String price = req.getParameter("price");
//        String sales = req.getParameter("sales");
//        String stock = req.getParameter("stock");
//        //图片暂且使用默认值，后面再修改
//        String defaultImg = "assets/images/product-image/default.jpg";
//
//        //数据格式校验
//        //方案一 使用java的正则表达式验证数据格式，
//        // 如果没有通过校验，就直接返回furn_add.jsp，并且在request域中提示错误信息，在前端jsp页面中展示
//
//        //方案二：直接将数据进行转换,
//        // 我们可以直接在构建Furn对象的地方捕获异常。这样，无论是哪一个数据格式出现错误都会被捕获异常。
//
//        //构建Furn对象
//        Furn furn = null;
//        try {
//            furn = new Furn(null, name, maker, new BigDecimal(price),
//                    new Integer(sales), new Integer(stock), defaultImg);
//        } catch (NumberFormatException e) {
//            req.setAttribute("errInfo", "添加数据格式不对");
//            //跳转到添加页面
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
//            return;
//        }

        //底层使用BeanUtils，完成自动封装JavaBean
        Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());

        int addRes = furnService.addFurn(furn);
        if (addRes != -1) {//插入成功,跳转到页面furn_manage.jsp
            //跳转之前，需要重新获取Furn列表，然后将furns集合放入到request域中，再请求转发
            //即需要重新走一下FurnServlet的list方法
            //req.getRequestDispatcher("/manage/furnServlet?action=list")
            // .forward(req, resp);

            //这里如果使用请求转发，当用户刷新页面时，会重新发出一次添加请求，造成数据重复提交
            //因此使用重定向
            //因为重定向实际是让浏览器重新发送请求，所以我们回送完整的url比较保险
            //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
            resp.sendRedirect(req.getContextPath() +
                    "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
        } else {
            resp.getWriter().write("添加失败");
        }
    }

    /**
     * 处理删除家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求删除的家居id
        String id = req.getParameter("id");

        //防止接收的id不是一个数字型的字符串
        furnService.deleteFurnById(DataUtils.parseInt(id, 0));
        //重定向到家居列表页-该地址由浏览器解析
        //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 处理显示单个家居信息的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求显示的家居id
        String id = req.getParameter("id");
        //在数据库中查询，得到furn对象
        Furn furn = furnService.queryFurnById(DataUtils.parseInt(id, 0));
        //将furn放入到request域中
        req.setAttribute("furn", furn);

        //如果请求带来的参数，是请求转发到下一个页面，在下一个页面可以使用param.pageNo获取

        //请求转发到furn_update.jsp中，在该页中显示furn信息
        //这里使用请求转发是因为如果使用重定向，当刷新页面之后就没有了request域中的信息
        req.getRequestDispatcher("/views/manage/furn_update.jsp")
                .forward(req, resp);
    }


    /**
     * 处理修改家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //自动填充Javabean-将提交修改的家居信息封装成Furn对象
        Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
        //调用updateFurn，更改数据
        furnService.updateFurn(furn);
        ////修改成功后重定向，显示列表家居
        //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");

        //这里我们考虑分页，并带上pageNo
        resp.sendRedirect(req.getContextPath() +
                "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 处理分页请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //调用service方法，获取page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        //将page对象放入request域中
        req.setAttribute("page", page);
        //请求转发到furn_manage.jsp
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }
}