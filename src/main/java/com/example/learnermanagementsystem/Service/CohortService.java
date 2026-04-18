package com.example.learnermanagementsystem.Service;

import com.example.learnermanagementsystem.DTO.CohortDTO;
import com.example.learnermanagementsystem.DTO.request.AssignLearnersToCohortRequest;
import com.example.learnermanagementsystem.DTO.request.CreateCohortRequest;
import com.example.learnermanagementsystem.DTO.request.CreateLearnerRequest;
import com.example.learnermanagementsystem.Exception.CohortNotFoundException;
import com.example.learnermanagementsystem.Exception.LearnerNotFoundException;
import com.example.learnermanagementsystem.Entity.Cohort;
import com.example.learnermanagementsystem.Entity.Learner;
import com.example.learnermanagementsystem.Repository.CohortRepository;
import com.example.learnermanagementsystem.Repository.LearnerRepository;
import com.example.learnermanagementsystem.mapper.CohortMapper;
import com.example.learnermanagementsystem.mapper.LearnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CohortService {

    @Autowired
    private CohortRepository cohortRepository;
    @Autowired
    private LearnerRepository learnerRepository;

    @Transactional
    public CohortDTO createCohort(CreateCohortRequest request) {
        Cohort saved = cohortRepository.save(CohortMapper.toEntity(request));
        return cohortRepository.findWithLearnersById(saved.getCohortId())
                .map(CohortMapper::toDto)
                .orElseGet(() -> CohortMapper.toDto(saved));
    }

    @Transactional(readOnly = true)
    public CohortDTO getCohortById(Long cohortId) {
        return cohortRepository.findWithLearnersById(cohortId)
                .map(CohortMapper::toDto)
                .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id: " + cohortId));
    }

    @Transactional
    public CohortDTO assignExistingLearnerToCohort(Long cohortId, Long learnerId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id: " + cohortId));
        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new LearnerNotFoundException("Learner not found with id: " + learnerId));
        CohortMapper.linkLearnerToCohort(cohort, learner);
        cohortRepository.save(cohort);
        return cohortRepository.findWithLearnersById(cohortId)
                .map(CohortMapper::toDto)
                .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id: " + cohortId));
    }

    @Transactional
    public CohortDTO assignLearnersToCohort(Long cohortId, AssignLearnersToCohortRequest request) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id: " + cohortId));

        List<Learner> managedLearners = new ArrayList<>();
        for (CreateLearnerRequest learnerRequest : request.getLearners()) {
            String email = learnerRequest.getLearnerEmail().trim().toLowerCase();
            Optional<Learner> existing = learnerRepository.findByLearnerEmail(email);
            if (existing.isPresent()) {
                managedLearners.add(existing.get());
            } else {
                Learner saved = learnerRepository.save(LearnerMapper.toEntity(learnerRequest));
                managedLearners.add(saved);
            }
        }

        for (Learner learner : managedLearners) {
            CohortMapper.linkLearnerToCohort(cohort, learner);
        }
        cohortRepository.save(cohort);

        return cohortRepository.findWithLearnersById(cohortId)
                .map(CohortMapper::toDto)
                .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id: " + cohortId));
    }
}
