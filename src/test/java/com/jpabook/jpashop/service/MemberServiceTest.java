package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("소현");

        // when
        Long saveId = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId ));
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("쏘");

        Member member2 = new Member();
        member2.setName("쏘");

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> memberService.join(member2)
        );
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

}