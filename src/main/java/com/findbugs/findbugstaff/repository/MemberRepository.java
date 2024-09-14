package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

        // 사용자 반환
        Optional<Member> findById(Long id);

}
