package com.example.learnermanagementsystem.DTO;

import java.util.ArrayList;
import java.util.List;

public class CohortDTO {
    private Long cohortId;
    private String cohortName;
    private String cohortDescription;
    private List<LearnerSummaryDTO> learners = new ArrayList<>();

    public Long getCohortId() {
        return cohortId;
    }

    public void setCohortId(Long cohortId) {
        this.cohortId = cohortId;
    }

    public String getCohortName() {
        return cohortName;
    }

    public void setCohortName(String cohortName) {
        this.cohortName = cohortName;
    }

    public String getCohortDescription() {
        return cohortDescription;
    }

    public void setCohortDescription(String cohortDescription) {
        this.cohortDescription = cohortDescription;
    }

    public List<LearnerSummaryDTO> getLearners() {
        return learners;
    }

    public void setLearners(List<LearnerSummaryDTO> learners) {
        this.learners = learners != null ? learners : new ArrayList<>();
    }
}
