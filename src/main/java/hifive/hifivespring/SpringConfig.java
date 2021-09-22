package hifive.hifivespring;


import hifive.hifivespring.repository.MemberRepository;
import hifive.hifivespring.repository.MemoryMemberRepository;
import hifive.hifivespring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

}
