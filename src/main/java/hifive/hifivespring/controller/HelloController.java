package hifive.hifivespring.controller;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String hellomvc(@RequestParam("name") String name, Model model) {
        //http://localhost:8999/hello-mvc?name=Fakeg!이렇게 되면
        // name은 Fakeg으로 넘어가고 이름은  name == Fakeg로 바뀌게 된다.
        //그리고 Model에 담긴다.
        model.addAttribute("name", name);

        // hello-template로 넘어가게 된다.
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody  // http에서 body부에 이 데이터를 직접 넣어주겠다라는 의미 --> 페이지 소스 확인 시 HTML 코드 안보임.
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name; // 출력 "hello Fakeg"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        //json 방식
        // xml 방식도 있다. 최근 json을 활용하는데 
        // 그 이유는 xml의 경우, 열고 닫고의 불편함과 무거운 단점이 있기 때문

        Hello hello = new Hello(); // 새로운 Hello 객체 생성
        hello.setName(name);       // 파라미터로 넘어온 name을 넘겨준다.
        return hello;

        // 객체를 주기때문에 객체가 오면  json 데이터방식으로 만들어서
        // http 응답에 반응하겠다라는게 기본 정책이다. (추가적인 부분도 있음)
        // 중요!!
        // ResponseBody를 사용한 api 방식에서 객체 형식으로 오면 HttpMessageConverter -> JsonConverter (기본 객체처리)
        // ResponseBody를 사용한 api 방식에서 문자 형식으로 오면 HttpMessageConverter -> StringConverter (기본 문자처리)
    }

    static class Hello {
        private String name; //Key == name

        // getter/setter를 Java Bean 규약(표준 방식)이라 함.
        //property 접근 방식이라고도 한다.
        public String getName() {
            return name;
        }

        // setter --> 넣을 때
        public void setName(String name) {
            this.name = name;
        }

    }
}
