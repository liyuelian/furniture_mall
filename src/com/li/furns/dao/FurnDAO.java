package com.li.furns.dao;

import com.li.furns.entity.Furn;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public interface FurnDAO {
    //返回所有的家居信息的集合，后面再考虑分页
    public List<Furn> queryFurns();
}
