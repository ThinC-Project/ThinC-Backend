package com.thincbackend.domain;

import com.thincbackend.dto.MemberFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String memberID;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Builder
    public Member(String memberID, String password, String nickname){
        this.memberID = memberID;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member createMember(MemberFormDto memberFormDto){
        Member user = Member.builder()
                .memberID(memberFormDto.getMemberID())
                .password(memberFormDto.getPassword())
                .nickname(memberFormDto.getNickname())
                .build();

        return user;
    }
}
