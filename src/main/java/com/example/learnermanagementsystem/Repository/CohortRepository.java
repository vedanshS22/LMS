package com.example.learnermanagementsystem.Repository;

import com.example.learnermanagementsystem.Entity.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long> {

    @Query("SELECT DISTINCT c FROM Cohort c LEFT JOIN FETCH c.learners WHERE c.cohortId = :id")
    Optional<Cohort> findWithLearnersById(@Param("id") Long id);
}
