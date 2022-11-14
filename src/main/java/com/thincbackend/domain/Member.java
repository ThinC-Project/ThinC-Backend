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
    private Long id;

    @Column(unique = true)
    private String MemberID;

    private String Password;

    @Column(unique = true)
    private String Nickname;

    @Builder
    public Member(String MemberID, String Password, String Nickname){
        this.MemberID = MemberID;
        this.Password = Password;
        this.Nickname = Nickname;
    }

    public static Member createMember(MemberFormDto memberFormDto){
        Member user = Member.builder()
                .MemberID(memberFormDto.getMemberID())
                .Password(memberFormDto.getPassword())
                .Nickname(memberFormDto.getNickname())
                .build();

        return user;
    }
}
