package com.findbugs.findbugstaff.repository;

import com.findbugs.findbugstaff.domain.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

}
