package com.li.furns.service;

import com.li.furns.entity.Member;

/**
 * @author 李
 * @version 1.0
 */
public interface MemberService {
    //注册用户
    public boolean registerMember(Member member);

    //判断用户名是否存在
    public boolean isExistsUsername(String username);
}
