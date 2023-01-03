package com.li.furns.test;

import com.li.furns.dao.MemberDAO;
import com.li.furns.dao.impl.MemberDAOImpl;
import com.li.furns.entity.Member;
import org.junit.jupiter.api.Test;

public class MemberDAOImplTest {

    private MemberDAO memberDAO = new MemberDAOImpl();

    @Test
    public void queryMemberByUsername() {
        if (memberDAO.queryMemberByUsername("admin") == null) {
            System.out.println("该用户名不存在...");
        } else {
            System.out.println("该用户存在");
        }
    }

    @Test
    public void saveMember() {
        Member member =
                new Member(null, "king", "king", "king@sohu.com");
        if (memberDAO.saveMember(member) == 1) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

    }

    @Test
    public void queryMemberByUsernameAndPassword() {
        //小技巧：可以自定义运行的快捷键（右上方的运行框要选到对应的测试用例）
        Member member = memberDAO.queryMemberByUsernameAndPassword
                ("admin", "admin");
        System.out.println("member=" + member);
    }

}
