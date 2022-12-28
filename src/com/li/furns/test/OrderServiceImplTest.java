package com.li.furns.test;

import com.li.furns.entity.Cart;
import com.li.furns.entity.CartItem;
import com.li.furns.service.OrderService;
import com.li.furns.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author 李
 * @version 1.0
 */
public class OrderServiceImplTest {
    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void saveOrder() {
        //构建cart对象
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "北欧风格小桌子", new BigDecimal(180), 10, new BigDecimal(1800)));
        cart.addItem(new CartItem(26, "简约风格小椅子", new BigDecimal(100), 5, new BigDecimal(500)));
        String orderId = orderService.saveOrder(cart, 2);
        System.out.println(orderId);
    }
}
