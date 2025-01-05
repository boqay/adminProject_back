package com.example.demo.member.entity;

import com.example.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @Comment("회원SEQ")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long memberSeq;
    
    @Comment("회원ID")
    @Column(name = "memberId", nullable = false, length = 50)
    private String memberId;

    @Column(name = "memberName", nullable = false, length = 25)
    @Comment("회원명")
    private String memberName;

    @Column(name = "passwd", nullable = false, length = 255)
    @Comment("비밀번호")
    private String password;

    @Column(name = "role", nullable = true, length = 20)
    @Comment("권한")
    private String role;

    @Column(name = "ph_no", length = 13)
    @Comment("휴대폰번호")
    private String phone;

    @Column(name = "addr", length = 255)
    @Comment("주소")
    private String address;

    @Column(name = "birth", length = 8)
    @Comment("생년월일")
    private String birth;

    @Column(name = "gender", length = 1)
    @Comment("성별")
    private String gender;

}
