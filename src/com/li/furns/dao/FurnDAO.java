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

    /**
     * 通过传入的id，返回furn表中对应的furn对象
     *
     * @param id id
     * @return 返回furn表中对应的furn对象
     */
    public Furn queryFurnById(int id);

    /**
     * 将传入的furn对象，根据id更新到furn表对应的字段
     *
     * @param furn 传入的furn对象
     * @return 返回受影响的行数
     */
    public int updateFurn(Furn furn);

    //分析：Page中只有 表的总记录数totalRow 以及
    // 返回的对象item集合 是可以直接从数据库中获取的
    // 因此将这两个属性的填充任务放在dao层

    /**
     * 查询furn表中的总记录数
     *
     * @return 返回总记录数
     */
    public int getTotalRow();

    /**
     * 获取当前页要显示的数据
     *
     * @param begin    表示从第几条记录开始获取，从0开始计算
     * @param pageSize 表示取出多少条记录
     * @return 返回对应对象的集合
     */
    public List<Furn> getPageItems(int begin, int pageSize);

    /**
     * 根据furnName返回符合条件的记录数
     *
     * @param furnName 搜索条件
     * @return 返回符合条件的记录数
     */
    public int getTotalRowByName(String furnName);

    /**
     * 根据 begin,pageSize,furnName，返回furn的记录
     *
     * @param furnName 搜索条件
     * @return 返回furn对象集合
     */
    public List<Furn> getPageItemByName(int begin, int pageSize, String furnName);
}
