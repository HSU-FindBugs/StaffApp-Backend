package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    // 동명이인이 있을 수 있으므로 List로 받습니다.
    List<Member> findByName(String name);

    // staffId 와 Page 를 기반으로 사용자 10명의 정보를 반환 + 방문일자 순으로 오름차순 정렬
    @Query("select m from Member m where m.manager = :staffId order by m.recentVisit asc")
    Slice<Member> findAllByStaffId(Long staffId, Pageable pageable);
}
