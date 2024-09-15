package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

    @Query("select b from Bug b where b.name = :name")
    Optional<Bug> findByName(@Param("name") String name);
}
