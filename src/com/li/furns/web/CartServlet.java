package com.li.furns.web;

import com.google.gson.Gson;
import com.li.furns.entity.Cart;
import com.li.furns.entity.CartItem;
import com.li.furns.entity.Furn;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;
import com.li.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李
 * @version 1.0
 */

public class CartServlet extends BasicServlet {
    //增加一个属性
    private FurnService furnService = new FurnServiceImpl();

    /**
     * 清空购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取session的购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.clear();
        }
        //回到请求清空的购物车页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 根据id删除购物车的某个家居信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取session中的购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.delItem(id);
        }
        //回到请求删除家居项的购物车页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 更新购物车的某个家居数量
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        int count = DataUtils.parseInt(req.getParameter("count"), 1);
        //获取session中的购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.updateCount(id, count);
        }
        //回到请求更新家居的购物车面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 添加家居数据到购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到添加的家居ID
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到id对应的Furn对象
        Furn furn = furnService.queryFurnById(id);
        if (furn == null) {//说明没有查到对应的家居信息
            return;
            //todo
        }
        if (furn.getStock() == 0) {//如果该家居库存为0
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        //根据furn构建CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());
        //从session获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {//说明当前的session没有cart对象
            //创建一个cart对象
            cart = new Cart();
            //将其放入到session中
            req.getSession().setAttribute("cart", cart);
        }
        //将cartItem加入到cart对象
        cart.addItem(item);
        //System.out.println("cart=" + cart);

        //添加完毕后需要返回到添加家居的页面
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }

    /**
     * 添加家居数据到购物车-Ajax方式
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //得到添加的家居ID
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到id对应的Furn对象
        Furn furn = furnService.queryFurnById(id);
        if (furn == null || furn.getStock() == 0) {//如果没有对应的家居信息或者该家居库存为0
            return;//结束业务
        }
        //根据furn构建CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());
        //从session获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {//如果当前的session没有cart对象
            //创建一个cart对象
            cart = new Cart();
            //将其放入到session中
            req.getSession().setAttribute("cart", cart);
        }
        //将cartItem加入到cart对象
        cart.addItem(item);

        //添加完毕后，将当前购物车的商品数量以json形式的数据返回
        //前端得到json后进行局部刷新即可
        //1.规定json格式{"cartTotalCount,3"}
        Map<String, Object> resultMap = new HashMap<>();
        //2.创建map
        resultMap.put("cartTotalCount", cart.getTotalCount());
        //3.转为json
        String resultJson = new Gson().toJson(resultMap);
        resp.getWriter().write(resultJson);
    }
}
