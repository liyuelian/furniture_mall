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

    /**
     * 将传入的Furn对象，保存到数据库
     *
     * @param furn Furn对象
     * @return 返回受影响的表行数，失败则返回-1
     */
    public int addFurn(Furn furn);

    /**
     * 通过传入的id，删除furn表中对应的记录
     *
     * @param id id
     * @return 返回受影响的行数
     */
    public int deleteFurnById(int id);
}
