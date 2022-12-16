package com.li.furns.service;

import com.li.furns.entity.Member;

/**
 * @author 李
 * @version 1.0
 */
public interface MemberService {
    //登录用户
    //相比于直接传递用户名和密码，传递一个Member对象拓展性会比较好一些
    public Member login(Member member);

    //注册用户
    public boolean registerMember(Member member);

    //判断用户名是否存在
    public boolean isExistsUsername(String username);
}
