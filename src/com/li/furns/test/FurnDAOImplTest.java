package com.li.furns.test;

import com.li.furns.dao.FurnDAO;
import com.li.furns.dao.impl.FurnDAOImpl;
import com.li.furns.entity.Furn;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnDAOImplTest {
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Test
    public void queryFurns() {
        List<Furn> furns = furnDAO.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void add() {
        Furn furn = new Furn(null, "台灯", "猫家居", new BigDecimal("20.00"), 33, 1, "assets/images/product-image/3.jpg");
        int add = furnDAO.addFurn(furn);
        if (add == -1) {
            System.out.println("添加数据失败");
        } else {
            System.out.println("添加数据成功");
        }
    }

    @Test
    public void deleteFurnById() {
        if (furnDAO.deleteFurnById(18) != 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void queryFurnById() {
        Furn furn = furnDAO.queryFurnById(1);
        System.out.println(furn);
    }

    @Test
    public void updateFurn() {
        Furn furn = new Furn(39,"king","皇家家居",new BigDecimal(999),
                88,99,"assets/images/product-image/default.jpg");
        int i = furnDAO.updateFurn(furn);
        System.out.println("i=" + i);
    }
}
