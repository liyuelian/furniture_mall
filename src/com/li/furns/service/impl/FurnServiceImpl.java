package com.li.furns.service.impl;

import com.li.furns.dao.FurnDAO;
import com.li.furns.dao.impl.FurnDAOImpl;
import com.li.furns.entity.Furn;
import com.li.furns.entity.Page;
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

    @Override
    public Furn queryFurnById(int id) {
        return furnDAO.queryFurnById(id);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        //先创建一个page对象，然后根据实际情况填充属性
        Page<Furn> page = new Page<>();

        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        int totalRow = furnDAO.getTotalRow();
        page.setTotalRow(totalRow);

        //总页数等于总记录数/每页记录数
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            //如果有余数就把总页数+1
            pageTotalCount++;
        }
        page.setPageTotalCount(pageTotalCount);

        //SELECT * FROM table_name
        //LIMIT 每页显示记录数*（第几页-1）,每页显示记录数
        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItems(begin, pageSize);
        page.setItems(pageItems);

        //还差一个url

        return page;
    }
}
