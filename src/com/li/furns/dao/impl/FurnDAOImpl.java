package com.li.furns.dao.impl;

import com.li.furns.dao.BasicDAO;
import com.li.furns.dao.FurnDAO;
import com.li.furns.entity.Furn;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {

    /**
     * @return 以集合形式返回所有家居信息
     */
    @Override
    public List<Furn> queryFurns() {
        //注意这里不要使用 * 查询表中所有信息
        //在字段img_path旁加上别名，可以解决Javabean和表的字段名不一致问题
        //因为反射是根据查询结果返回的字段名来进行Javabean的构建的
        String sql =
                "SELECT `id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path` imgPath " +
                        "FROM `furn`;";
        return queryMulti(sql, Furn.class);
    }

    /**
     * 往furn表中添加家居信息
     *
     * @param furn 家居对象
     * @return 返回的值是受影响的行数，操作失败则返回-1
     */
    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn(`id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path`)" +
                "VALUES(NULL , ?, ? , ? , ? , ? , ?);";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurnById(int id) {
        String sql = "DELETE FROM `furn` WHERE `id`=?;";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(int id) {
        String sql = "SELECT `id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path` AS imgPath " +
                "FROM `furn` WHERE id=?";
        return querySingle(sql, Furn.class, id);
    }

    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATE `furn` " +
                "SET `name`=?, `maker`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? " +
                "WHERE `id`=?";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath(), furn.getId());
    }

    @Override
    public int getTotalRow() {
        String sql = "SELECT COUNT(*) FROM `furn`";
        //return (Integer) queryScalar(sql);==>会有隐患，可能出现ClassCastException异常
        return ((Number) queryScalar(sql)).intValue();
    }

    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {
        String sql = "SELECT `id` , `name` , `maker` , `price` , `sales` ," +
                " `stock` , `img_path` AS imgPath " +
                "FROM `furn` " +
                "LIMIT ?,?";
        return queryMulti(sql, Furn.class, begin, pageSize);
    }

    @Override
    public int getTotalRowByName(String furnName) {
        String sql = "SELECT  COUNT(*) " +
                "FROM `furn` " +
                "WHERE `name` LIKE ?";
        return ((Number) queryScalar(sql, "%" + furnName + "%")).intValue();
    }

    @Override
    public List<Furn> getPageItemByName(int begin, int pageSize, String furnName) {
        String sql = "SELECT  `id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path` AS imgPath " +
                "FROM `furn` " +
                "WHERE `name` LIKE ? LIMIT ?,?";
        return queryMulti(sql, Furn.class, "%" + furnName + "%", begin, pageSize);
    }
}
