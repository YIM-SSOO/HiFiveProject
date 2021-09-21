package hifive.hifivespring.repository;

import hifive.hifivespring.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트는 서로 순서와 의존에 관계없이 설계되어야한다.
    // 그러기 위해선  하나의 테스트가 끝날때마다 뭔가 저장소나 공용데이터들을 깔끔하게 지워줘야 문제가 없다.
    @AfterEach
    public void afterEach() {
        repository.clearStore(); //테스트가 실행되고 끝날때만다 한번씩 저장소를 지워주기때문에 순서가 상관없어진다.
    }

    @Test
    public void save() {
        //given
        Member member = new Member(); // 저장이 잘 되는지 확인
        member.setName("spring");

        repository.save(member);

        //repository.findId(member.getId()).get();

        Member result = repository.findById(member.getId()).get();
        //  System.out.println("result = " + (result == member));
        //Assertions.assertEquals( member, result);
        assertThat(result).isEqualTo(member); // == Assertions.asserThat(memeber).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // repository.findByName("spring1");
        // Optional<Member> result = repository.findName("spring1).get();
        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
