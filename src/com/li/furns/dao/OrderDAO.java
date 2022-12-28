package com.li.furns.dao;

import com.li.furns.entity.Order;

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
}
