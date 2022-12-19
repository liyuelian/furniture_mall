package com.li.furns.service.impl;

import com.li.furns.dao.FurnDAO;
import com.li.furns.dao.impl.FurnDAOImpl;
import com.li.furns.entity.Furn;
import com.li.furns.service.FurnService;

import java.util.List;

public class FurnServiceImpl implements FurnService {
    //定义属性FurnDAO对象
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryFurns();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurnById(int id) {
        return furnDAO.deleteFurnById(id);
    }
}
