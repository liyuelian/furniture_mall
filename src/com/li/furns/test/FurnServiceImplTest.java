package com.li.furns.test;

import com.li.furns.dao.FurnDAO;
import com.li.furns.dao.FurnDAOImpl;
import com.li.furns.entity.Furn;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FurnServiceImplTest {
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Test
    public void queryFurns() {
        List<Furn> furns = furnDAO.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }
}
