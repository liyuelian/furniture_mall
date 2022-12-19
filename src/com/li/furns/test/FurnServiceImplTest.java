package com.li.furns.test;

import com.li.furns.entity.Furn;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnServiceImplTest {
    private FurnService furnService = new FurnServiceImpl();

    @Test
    public void queryFurns() {
        List<Furn> furns = furnService.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void add() {
        int add = furnService.addFurn(new Furn(null, "小台灯", "猫家居",
                new BigDecimal("99.00"), 55, 24,
                "assets/images/product-image/3.jpg"));
        if (add == -1) {
            System.out.println("添加失败");
        } else {
            System.out.println("添加成功");
        }
    }

    @Test
    public void deleteFurnById() {
        if (furnService.deleteFurnById(24) != -1) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void queryFurnById() {
        Furn furn = furnService.queryFurnById(1);
        System.out.println(furn);
    }

    @Test
    public void updateFurn() {
        int i = furnService.updateFurn(new Furn(38, "queen", "皇家家居", new BigDecimal(888),
                99, 21, "assets/images/product-image/default.jpg"));
        System.out.println("i=" + i);
    }
}
