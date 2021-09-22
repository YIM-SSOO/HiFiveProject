package hifive.hifivespring.repository;

import hifive.hifivespring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {


    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;     // key 값을 0,1,2 .. 만들어주는 것이 sequence

    @Override
    public Member save(Member member) {
        member.setId(++sequence);             // member를 save할 때 시퀀스 값 하나를 올려주고
        store.put(member.getId(), member);    // store에다가 put해서 member.getId()로 넣고 member.
        return member;                        // store에다가 넣기 전에 member의 id값을 세팅해주고 (이름은 세이브하기전에 넘어온 상태)
        // 그럼 Map에다 저장이 된다. 그렇게해서 결과를 반환해줕다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))   //람다식
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //values == member
    }

    public void clearStore() {
        store.clear();
    }
}
