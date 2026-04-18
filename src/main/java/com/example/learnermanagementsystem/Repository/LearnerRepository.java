package com.example.learnermanagementsystem.Repository;

import com.example.learnermanagementsystem.Entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access only: use {@link java.util.Optional#empty()} when no row exists.
 * Services translate empty results into domain exceptions for the REST layer.
 */
@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {

    Optional<Learner> findByLearnerEmail(String learnerEmail);

    boolean existsByLearnerEmail(String learnerEmail);

    @Query("SELECT DISTINCT l FROM Learner l LEFT JOIN FETCH l.cohorts WHERE l.learnerId = :id")
    Optional<Learner> findWithCohortsById(@Param("id") Long id);

    @Query("SELECT DISTINCT l FROM Learner l LEFT JOIN FETCH l.cohorts")
    List<Learner> findAllWithCohorts();

    @Query("SELECT DISTINCT l FROM Learner l LEFT JOIN FETCH l.cohorts WHERE LOWER(l.learnerName) = LOWER(:name)")
    List<Learner> findAllWithCohortsByLearnerNameIgnoreCase(@Param("name") String name);
}