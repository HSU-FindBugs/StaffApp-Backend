package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    //@Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
    //"FROM Visit v WHERE v.member.id = :memberId AND FUNCTION('DATE', v.visitTime) = :date")

    @Query("select case when count(v) > 0 then true else false end "+
            "from Visit v where v.member.id = :memberId and function('DATE', v.visitedAt) = :date and v.manager.id = :staffId")
    boolean existsByIdAndLocalDate(@Param("memberId") Long memberId, @Param("staffId") Long staffId, @Param("date") LocalDate date);

}
