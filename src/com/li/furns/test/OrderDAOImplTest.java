package com.li.furns.test;

import com.li.furns.dao.OrderDAO;
import com.li.furns.dao.impl.OrderDAOImpl;
import com.li.furns.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 李
 * @version 1.0
 */
public class OrderDAOImplTest {
    private OrderDAO orderDAO = new OrderDAOImpl();

    @Test
    public void saveOrder() {
        Order order = new Order("sn000002", new Date(),
                new BigDecimal(300), 0, 1);
        System.out.println(orderDAO.saveOrder(order));
    }
}
