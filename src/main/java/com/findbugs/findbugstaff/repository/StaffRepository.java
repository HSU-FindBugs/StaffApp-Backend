package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Address;
import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // 직원 프로필 이미지 반환
    @Query("select s.profileUrl from Staff s where s.id = :staffId")
    Optional<String> findProfileById(@Param("staffId") Long id);

    // 직원 직급 반환
    @Query("select s.position from Staff s where s.id = :staffId")
    Optional<String> findPositionById(@Param("staffId")Long id);

    // 직원 전화번호 반환
    @Query("select s.phoneNumber from Staff s where s.id = :staffId")
    Optional<String> findPhoneById(@Param("staffId")Long id);

    // 직원 담당 주소 반환
    @Query("select s.territory from Staff s where s.id = :staffId")
    Optional<Address> findTerritoryById(@Param("staffId") Long id);

    // 직원이 담당하는 사용자 반환
    @Query("select m from Member m where m.manager = :manager")
    List<Member> findByManager(@Param("manager") Staff manager);

    // 직원이 담당하는 사용자의 수 반환
    @Query("select count(m) from Member m where m.manager.id = :managerId")
    Long countByManager(@Param("managerId") Long id);


}
