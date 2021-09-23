package hifive.hifivespring.service;

import hifive.hifivespring.domain.Member;
import hifive.hifivespring.repository.MemberRepository;
import hifive.hifivespring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
        //데이터베이스는 트랙젝션의 개념이 있다.
        // @Transactional을 Test 케이스에 달면 테스트를 실행할때  트랜젝션을 먼저 실행을 한다.
        //그리고 디비의 데이터를 인서트쿼리를 하고 다 넣은 다음에 테스트가 끝나면 롤백을 해준다!
        //그래서 디비에 넣었던 데이터가 반영이 안되고 깔끔하게 지워진다.(실제 반영이 되지 않음)
    // 즉 반복해서 테스트를 실행할 수 있어진다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    
//회원가입
    @Test
    void join() {
        //given - 이런 상황이 주어져서
        Member member = new Member();
        member.setName("abc");

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

    }

}