package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // 직원 반환
    Optional<Staff> findById(Long id);

    // 직원 프로필 이미지 반환
    @Query("select s.profileUrl from Staff s where s.id = :staffId")
    Optional<String> findProfileById(Long id);

    // 직원 직급 반환
    @Query("select s.position from Staff s where s.id = :staffId")
    Optional<String> findPositionById(Long id);

    // 직원 전화번호 반환
    @Query("select s.phoneNumber from Staff s where s.id = :staffId")
    Optional<String> findPhoneById(Long id);

    // 직원 담당 주소 반환
    @Query("select s.territory from Staff s where s.id = :staffId")
    Optional<String> findTerritoryById(@Param("staffId") Long id);


}
