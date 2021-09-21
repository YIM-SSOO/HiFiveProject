package hifive.hifivespring.service;

import hifive.hifivespring.domain.Member;
import hifive.hifivespring.repository.MemberRepository;
import hifive.hifivespring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
         private final MemberRepository memberRepository;

         // 내부에서 직접넣지 않고 외부에서 넣는 방식으로 바꾼것
         public MemberService(MemberRepository memberRepository){
             this.memberRepository = memberRepository;
         }

    //회원가입
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복회원 검증

        memberRepository.save(member);
        return member.getId();

        // 중복되는 이름을 가진 회원X
        //memberRepository.findByName(member.getName()); ==
        //Optional<Member> result = memberRepository.findByName(member.getName());
       /* memberRepository.findByName(member.getName())
               .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        */
        /*
            result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
            } );
        */
        //값이 있으면 멤버가 들어옴 -> 멤버에 값이 있으면 throw new IllegalAccessException에서 존재하는 회원
        // ifPresent() 얘가 null이 아니라 어떤 값이 있으면 이 로직이나 동작을 하는 것
        // 이것은 Optional이기때문에 가능, Optional은 감싸있기 때문이다.


    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
