package com.li.furns.dao;

import com.li.furns.entity.Member;

/**
 * @author 李
 * @version 1.0
 */
public interface MemberDAO {

    //提供一个通过用户名返回对应的Member的方法
    public Member queryMemberByUsername(String username);

    //提供一个保存Member对象到数据库member表的方法
    public int saveMember(Member member);
}
