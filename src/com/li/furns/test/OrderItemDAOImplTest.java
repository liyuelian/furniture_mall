package com.li.furns.test;

import com.li.furns.dao.OrderItemDAO;
import com.li.furns.dao.impl.OrderItemDAOImpl;
import com.li.furns.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public class OrderItemDAOImplTest {
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Test
    public void saveOrderItem() {
        OrderItem orderItem = new OrderItem(null, "北欧小沙发", new BigDecimal(200),
                3, new BigDecimal(600), "sn000001");
        System.out.println(orderItemDAO.saveOrderItem(orderItem));
    }

    @Test
    public void queryOrderItemByOrderId() {
        List<OrderItem> orderItems = orderItemDAO.queryOrderItemByOrderId("16722358644142");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
    }
}
