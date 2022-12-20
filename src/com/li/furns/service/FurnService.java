package com.li.furns.service;

import com.li.furns.entity.Furn;
import com.li.furns.entity.Page;

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
     *
     * @param furn furn对象
     * @return 返回修改表的行数，失败则返回-1
     */
    public int addFurn(Furn furn);

    /**
     * 根据id删除对应的家居信息
     *
     * @param id id
     * @return 返回修改表的行数
     */
    public int deleteFurnById(int id);

    /**
     * 根据id返回家居信息
     *
     * @param id id
     * @return 返回furn表中对应的furn对象
     */
    public Furn queryFurnById(int id);

    /**
     * 通过传入的furn对象，修改furn表对应的记录的字段值
     *
     * @param furn 传入的furn对象
     * @return 返回受影响的行数
     */
    public int updateFurn(Furn furn);

    /**
     * 根据传入的pageNo和pageSize，返回对应的page对象
     *
     * @param pageNo   表示第几页
     * @param pageSize 表示一页取出多少条记录
     * @return 返回对应的page对象
     */
    public Page<Furn> page(int pageNo, int pageSize);
}
