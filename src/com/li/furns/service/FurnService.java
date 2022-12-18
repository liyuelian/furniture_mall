package com.li.furns.service;

import com.li.furns.entity.Furn;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public interface FurnService {
    /**
     * 返回家居信息
     *
     * @return 返回家居对象集合
     */
    public List<Furn> queryFurns();

    /**
     * 添加Furn对象到数据库
     * @param furn furn对象
     * @return 返回修改表的行数，失败则返回-1
     */
    public int addFurn(Furn furn);

}
