package hifive.hifivespring;



import hifive.hifivespring.aop.TimeTraceAop;
import hifive.hifivespring.repository.JpaMemberRepository;
import hifive.hifivespring.repository.MemberRepository;
import hifive.hifivespring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    //private DataSource dataSource;

    /*
    /*@Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }*/

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Bean
    public MemberService memberService(){

        return new MemberService(memberRepository);
    }


    //빈으로 등록해주는 방법 (시간 측정)
    /*@Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }*/
//    @Bean
//    public MemberRepository memberRepository(){

//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

}
