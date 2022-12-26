package com.li.furns.entity;

import java.util.HashMap;
import java.util.Set;

/**
 * Cart就是一个购物车，包含很多CartItem对象
 *
 * @author 李
 * @version 1.0
 */
public class Cart {
    //定义属性
    //包含多个CartItem对象，使用HashMap来保存
    private HashMap<Integer, CartItem> items = new HashMap<>();

    public int getTotalCount() {
        //因为前端每点击一次添加商品，购物车显示就会调用getTotalCount方法，
        //如果不置0，数量相当是重复添加
        int totalCount = 0;
        //遍历购物车，返回商品总数量
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            totalCount += ((CartItem) items.get(id)).getCount();
        }
        return totalCount;
    }

    //添加家居CartItem到Cart
    public void addItem(CartItem cartItem) {
        //添加cartItem到Cart前要先判断-该item是第一次添加还是二次以后添加
        //使用家居id在items中找有没有对应家居
        CartItem item = items.get(cartItem.getId());
        if (null == item) {//说明当前购物车还没有这个cartItem
            //添加该cartItem到购物车Cart中去
            items.put(cartItem.getId(), cartItem);
        } else {//当前购物车已经有这个cartItem
            //数量增加1
            item.setCount(item.getCount() + 1);
            //修改总价
            //item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
            item.setTotalPrice(item.getTotalPrice().add(item.getPrice()));
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
