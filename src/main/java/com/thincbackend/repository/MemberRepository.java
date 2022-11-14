package com.thincbackend.repository;

import com.thincbackend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberID(String MemberID);
}
