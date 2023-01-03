package com.li.furns.dao;

import com.li.furns.entity.Order;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public interface OrderDAO {
    /**
     * 将传入的order对象保存到数据表order表
     *
     * @param order order对象
     * @return 返回操作影响的行数
     */
    public int saveOrder(Order order);

    /**
     * 根据用户id，查询该用户的所有订单Order
     *
     * @param id 用户id
     * @return 返回订单
     */
    public List<Order> queryOrderByMemberId(int id);


}
