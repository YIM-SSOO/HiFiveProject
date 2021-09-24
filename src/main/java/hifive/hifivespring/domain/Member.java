package hifive.hifivespring.domain;


import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 값을 넣어주면 DB가 ID를 알아서 생성해주는것 ==strategy = GenerationType.IDENTITY
    private Long id;

    // 컬럼명이 username
   // @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
