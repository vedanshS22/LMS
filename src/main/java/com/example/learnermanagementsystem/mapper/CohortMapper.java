package com.example.learnermanagementsystem.mapper;

import com.example.learnermanagementsystem.DTO.CohortDTO;
import com.example.learnermanagementsystem.DTO.LearnerSummaryDTO;
import com.example.learnermanagementsystem.DTO.request.CreateCohortRequest;
import com.example.learnermanagementsystem.Entity.Cohort;
import com.example.learnermanagementsystem.Entity.Learner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CohortMapper {

    private CohortMapper() {
    }

    public static Cohort toEntity(CreateCohortRequest request) {
        Cohort cohort = new Cohort();
        cohort.setCohortName(request.getCohortName().trim());
        if (request.getCohortDescription() != null) {
            cohort.setCohortDescription(request.getCohortDescription().trim());
        }
        return cohort;
    }

    public static CohortDTO toDto(Cohort cohort) {
        CohortDTO dto = new CohortDTO();
        dto.setCohortId(cohort.getCohortId());
        dto.setCohortName(cohort.getCohortName());
        dto.setCohortDescription(cohort.getCohortDescription());
        if (cohort.getLearners() != null) {
            List<LearnerSummaryDTO> summaries =
                    cohort.getLearners().stream().map(LearnerMapper::toSummary).collect(Collectors.toList());
            dto.setLearners(summaries);
        }
        return dto;
    }

    public static void linkLearnerToCohort(Cohort cohort, Learner learner) {
        boolean alreadyLinked = cohort.getLearners().stream()
                .anyMatch(l -> Objects.equals(l.getLearnerId(), learner.getLearnerId())
                        && l.getLearnerId() != null);
        if (!alreadyLinked) {
            cohort.getLearners().add(learner);
        }
        boolean cohortLinked = learner.getCohorts().stream()
                .anyMatch(c -> Objects.equals(c.getCohortId(), cohort.getCohortId())
                        && c.getCohortId() != null);
        if (!cohortLinked) {
            learner.getCohorts().add(cohort);
        }
    }
}
