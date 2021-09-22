package hifive.hifivespring.controller;


import hifive.hifivespring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    //private final MemberService memberService = new MemberService();

    // 스프링 컨테이너에 등록
    private final MemberService memberService;

    // Dependency Injection
    @Autowired   // 생성자
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


}
