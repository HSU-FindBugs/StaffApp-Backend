package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

        // 사용자 반환
        Optional<Member> findById(Long id);

}
