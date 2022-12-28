package com.li.furns.dao.impl;

import com.li.furns.dao.BasicDAO;
import com.li.furns.dao.OrderDAO;
import com.li.furns.entity.Order;

/**
 * @author 李
 * @version 1.0
 */
public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO {
    @Override
    public int saveOrder(Order order) {
        String sql =
                "INSERT INTO `order`(`id`,`create_time`,`price`,`status`,`member_id`) " +
                        "VALUES(?,?,?,?,?)";
        return update(sql, order.getId(), order.getCreateTime(),
                order.getPrice(), order.getStatus(), order.getMemberId());

    }
}
