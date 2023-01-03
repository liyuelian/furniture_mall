package com.li.furns.dao.impl;

import com.li.furns.dao.BasicDAO;
import com.li.furns.dao.MemberDAO;
import com.li.furns.entity.Member;

/**
 * @author 李
 * @version 1.0
 */
public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO {

    /**
     * 通过用户名和密码返回对应的Member对象
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回值为对应的Member对象，如果不存在则返回null
     */
    @Override
    public Member queryMemberByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM `member` WHERE `username`=? AND `password`=MD5(?);";
        return querySingle(sql, Member.class, username, password);
    }

    /**
     * 通过用户名返回对应的Member
     *
     * @param username 用户名
     * @return 对应的Member，如果没有对应的Member，就返回null
     */
    @Override
    public Member queryMemberByUsername(String username) {
        //建议sql语句先在SQL软件中进行测试，在拿到程序中
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member`" +
                "WHERE `username`=?;";
        return querySingle(sql, Member.class, username);
    }

    /**
     * 保存一个用户信息
     *
     * @param member 传入的Member对象
     * @return 返回-1就是失败，返回其他的数据就是受影响的行数
     */
    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO member (`username`,`password`,`email`)" +
                "VALUES(?,MD5(?),?);";
        return update(sql, member.getUsername(), member.getPassword(), member.getEmail());
    }
}
