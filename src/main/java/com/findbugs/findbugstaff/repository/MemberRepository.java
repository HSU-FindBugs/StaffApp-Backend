package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    // 동명이인이 있을 수 있으므로 List로 받습니다.
    List<Member> findByName(String name);
}
