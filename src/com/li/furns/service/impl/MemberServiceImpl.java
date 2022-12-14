package com.li.furns.service.impl;

import com.li.furns.dao.MemberDAO;
import com.li.furns.dao.impl.MemberDAOImpl;
import com.li.furns.entity.Member;
import com.li.furns.service.MemberService;

/**
 * @author 李
 * @version 1.0
 */
public class MemberServiceImpl implements MemberService {
    //定义一个MemberDAO属性，它指向它的实现类
    private MemberDAO memberDAO = new MemberDAOImpl();

    @Override
    public boolean registerMember(Member member) {
        //saveMember方法返回1，代表保存成功，就返回true，否则返回false
        return memberDAO.saveMember(member) == 1;
    }

    @Override
    public boolean isExistsUsername(String username) {
        //如果看某个使用ctrl+b，将会定位到该方法的编译类型
        // 如果使用ctrl+alt+b，将会定位到该方法实现的位置（运行类型）
        // 如果接口中声明的方法被多个子类实现了，那么使用ctrl+alt+b就会列出所有实现该方法的子类

        //如果返回的用户不为空，就返回true，如果该用户返回null，就返回false
        return memberDAO.queryMemberByUsername(username) != null;
    }
}
