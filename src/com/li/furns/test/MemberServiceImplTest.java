package com.li.furns.test;

import com.li.furns.entity.Member;
import com.li.furns.service.MemberService;
import com.li.furns.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * @author 李
 * @version 1.0
 */
public class MemberServiceImplTest {
    private MemberService memberService = new MemberServiceImpl();

    @Test
    public void isExistsUsername() {
        if (memberService.isExistsUsername("admin")) {
            System.out.println("用户存在");
        } else {
            System.out.println("用户不存在");
        }
    }
    @Test
    public void registerMember(){
        //构建一个Member对象
        Member member = new Member(null, "queen", "queen", "queen@163.com");
        if (memberService.registerMember(member)) {
            System.out.println("用户注册成功");
        } else {
            System.out.println("用户注册失败");
        }
    }
}
