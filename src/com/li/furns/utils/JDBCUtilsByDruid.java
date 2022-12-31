package com.li.furns.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 基于Druid数据库连接池的工具类
 */
public class JDBCUtilsByDruid {

    private static DataSource ds;
    //定义属性ThreadLocal，这里存放一个Connection
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    //在静态代码块完成ds的初始化
    //静态代码块在加载类的时候只会执行一次，因此数据源也只会初始化一次
    static {
        Properties properties = new Properties();
        try {
            //因为我们是web项目，它的工作目录不在src下面，文件的加载需要使用类加载器
            properties.load(JDBCUtilsByDruid.class.getClassLoader()
                    .getResourceAsStream("druid.properties"));
            //properties.load(new FileInputStream("src\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //编写getConnection方法
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }

    /**
     * 获取连接方法
     * 从ThreadLocal中获取connection，
     * 从而保证在同一个线程中获取的是同一个Connection
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        Connection connection = threadLocalConn.get();
        if (connection == null) {//说明当前的threadLocalConn没有连接
            //就从数据库连接池中获取一个连接，放到ThreadLocal中
            try {
                connection = ds.getConnection();
                //设置为手动提交，即不要自动提交
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    /**
     * 提交事务
     */
    public static void commit() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {//确保该连接是有效的
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();//将连接释放回连接池
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //1.当提交后，需要把connection从threadLocalConn中清除掉
            //2.否则会造成ThreadLocalConn长时间持有该连接，会影响效率
            //3.也因为我们Tomcat底层使用的是线程池技术
            threadLocalConn.remove();
        }
    }

    /**
     * 回滚，回滚的是和connection相关的dml操作
     */
    public static void rollback() {
        Connection connection = threadLocalConn.get();
        if (connection != null) {//保证当前的连接是有效的
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        threadLocalConn.remove();
    }

    //关闭连接（注意：在数据库连接池技术中，close不是真的关闭连接，而是将Connection对象放回连接池中）
    public static void close(ResultSet resultSet, Statement statemenat, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statemenat != null) {
                statemenat.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
