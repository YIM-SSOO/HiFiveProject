package hifive.hifivespring.repository;

import hifive.hifivespring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    //JPA를 사용하려면 EntityManager를 주입받아야한다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //persist = 영속하다. 영구 저장하다
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 조회할 타입과 식별자 pk값을 넣으면 조회가 된다. ==> select
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);

    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }
    @Override
    public List<Member> findAll() {
        //"select m from Member m" == JPQL이라는 쿼리언어, 테이블 대상이 아닌 객체를 대상으로 쿼리를 날린다.
        // 그럼 이것이 sql로 번역이 된다.
        //Entity를 대상으로 쿼리를 날리는 것.
        //1. Member Entity를 조회해
        //2. select의 대상은 Member 객체 자체를 select 한다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
