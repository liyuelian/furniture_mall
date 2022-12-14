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
