package com.example.learnermanagementsystem.mapper;

import com.example.learnermanagementsystem.DTO.CohortSummaryDTO;
import com.example.learnermanagementsystem.DTO.LearnerDTO;
import com.example.learnermanagementsystem.DTO.LearnerSummaryDTO;
import com.example.learnermanagementsystem.DTO.request.CreateLearnerRequest;
import com.example.learnermanagementsystem.Entity.Cohort;
import com.example.learnermanagementsystem.Entity.Learner;

import java.util.List;
import java.util.stream.Collectors;

public final class LearnerMapper {

    private LearnerMapper() {
    }

    public static Learner toEntity(CreateLearnerRequest request) {
        Learner learner = new Learner();
        learner.setLearnerName(request.getLearnerName().trim());
        learner.setLearnerEmail(request.getLearnerEmail().trim().toLowerCase());
        learner.setLearnerPhone(request.getLearnerPhone().trim());
        return learner;
    }

    public static LearnerDTO toDto(Learner learner) {
        LearnerDTO dto = new LearnerDTO();
        dto.setLearnerId(learner.getLearnerId());
        dto.setLearnerName(learner.getLearnerName());
        dto.setLearnerEmail(learner.getLearnerEmail());
        dto.setLearnerPhone(learner.getLearnerPhone());
        if (learner.getCohorts() != null) {
            List<CohortSummaryDTO> cohorts = learner.getCohorts().stream()
                    .map(LearnerMapper::toCohortSummary)
                    .collect(Collectors.toList());
            dto.setCohorts(cohorts);
        }
        return dto;
    }

    public static CohortSummaryDTO toCohortSummary(Cohort cohort) {
        CohortSummaryDTO dto = new CohortSummaryDTO();
        dto.setCohortId(cohort.getCohortId());
        dto.setCohortName(cohort.getCohortName());
        dto.setCohortDescription(cohort.getCohortDescription());
        return dto;
    }

    public static LearnerSummaryDTO toSummary(Learner learner) {
        LearnerSummaryDTO dto = new LearnerSummaryDTO();
        dto.setLearnerId(learner.getLearnerId());
        dto.setLearnerName(learner.getLearnerName());
        dto.setLearnerEmail(learner.getLearnerEmail());
        return dto;
    }
}
