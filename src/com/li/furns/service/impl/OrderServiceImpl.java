package com.li.furns.service.impl;

import com.li.furns.dao.FurnDAO;
import com.li.furns.dao.OrderDAO;
import com.li.furns.dao.OrderItemDAO;
import com.li.furns.dao.impl.FurnDAOImpl;
import com.li.furns.dao.impl.OrderDAOImpl;
import com.li.furns.dao.impl.OrderItemDAOImpl;
import com.li.furns.entity.*;
import com.li.furns.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 李
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public String saveOrder(Cart cart, int memberId) {
        //这里的业务相对而言比较综合
        //任务：将cart购物车的数据以order和orderItem的形式保存到数据库中

        /**
         * 因为生成订单会操作多张表，设计到多表事务问题，使用ThreadLocal+MySQL事务机制+过滤器Filter
         * 因为事务处理考虑的点比较多，这里先不处理，后面专门处理
         */

        //1.构建一个对应的order对象
        //先生成一个UUID，表示当前的订单号。要保证订单号是唯一的
        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order = new Order(orderId, new Date(), cart.getCartTotalPrice(), 0, memberId);
        //将order对象保存到数据库order表中
        orderDAO.saveOrder(order);
        //2.通过cart对象，遍历出CartItem家居项，构建orderItem对象
        HashMap<Integer, CartItem> items = cart.getItems();
        //遍历hm
        /**
         * 遍历方法一：entrySet
         * 通过entrySet的方法遍历items，取出cartItem
         * for(Map.Entry<Integer, CartItem> entry : items.entrySet()) {
         *       CartItem item = entry.getValue();
         *       ...
         *  }
         */
        //遍历方法二：keySet
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            //获取家居项
            CartItem item = items.get(id);
            //构建orderItem对象
            OrderItem orderItem = new OrderItem(null, item.getName(), item.getPrice(),
                    item.getCount(), item.getTotalPrice(), orderId);
            //将orderItem对象保存到数据库表order_item中
            orderItemDAO.saveOrderItem(orderItem);
            //更新furn表的对应记录的sales销量，stock存量
            //(1)获取到furn对象
            Furn furn = furnDAO.queryFurnById(id);
            //(2)更新furn对象的sales销量，stock存量
            furn.setSales(furn.getSales() + item.getCount());
            furn.setStock(furn.getStock() - item.getCount());
            //(3)更新到数据表中
            furnDAO.updateFurn(furn);
        }
        //清空购物车
        cart.clear();
        //返回生成的订单号
        return orderId;
    }
}
