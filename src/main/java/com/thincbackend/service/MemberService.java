package com.thincbackend.service;

import com.thincbackend.domain.Member;
import com.thincbackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberID(member.getMemberID());
        if(findMember!=null){
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
    }

    public Member loadMemberByMemberId(String ID){
        Member member = memberRepository.findByMemberID(ID);

        return Member.builder()
                .memberID(member.getMemberID())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .build();
    }
}
