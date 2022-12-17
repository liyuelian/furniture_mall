package com.li.furns.dao;

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
}
