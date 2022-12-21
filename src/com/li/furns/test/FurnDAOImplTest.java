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
        Furn furn = new Furn(39, "king", "皇家家居", new BigDecimal(999),
                88, 99, "assets/images/product-image/default.jpg");
        int i = furnDAO.updateFurn(furn);
        System.out.println("i=" + i);
    }

    @Test
    public void getTotalRow() {
        int totalRow = furnDAO.getTotalRow();
        System.out.println(totalRow);
    }

    @Test
    public void getPageItems() {
        List<Furn> pageItems = furnDAO.getPageItems(1, 3);
        for (Furn pageItem : pageItems) {
            System.out.println(pageItem);
        }
    }

    @Test
    public void getPageTotalCountByName() {
        System.out.println("根据名字’沙发‘搜索=>" + furnDAO.getTotalRowByName("沙发"));
    }

    @Test
    public void getPageItemByName() {
        //从第0条记录开始，每页显示5个记录
        List<Furn> furns = furnDAO.getPageItemByName(0, 5, "沙发");
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }
}
