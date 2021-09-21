package hifive.hifivespring.service;

import hifive.hifivespring.domain.Member;

import hifive.hifivespring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {


//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        //테스트가 실행할 때마다 각각 생성해준다
        //독립적으로 실행되야하기때문이다.
        //이러면 같은 memoryMemberrepository가 사용이되는 것이다.

        //DI (외부에서 주입시켜주는 것)
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given - 이런 상황이 주어져서
        Member member = new Member();
        member.setName("hello");

        //when - 이것을 실행 했을 때 --> 이걸 검증하는거구나
        Long saveId = memberService.join(member);


        //then - 결과가 이게 나와야해
        //memberService.findOne(saveId);=>
        //Optional<Member> one = Optional.of(memberService.findOne(saveId).get()); =>
        Member findMember = memberService.findOne(saveId).get();
        //Assertions.assertThat(member.getName()).isEqualTo(findMember.getName()); =>
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void valid() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //assertThrows(NullPointerException.class, () -> memberService.join(member2));
        /*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}