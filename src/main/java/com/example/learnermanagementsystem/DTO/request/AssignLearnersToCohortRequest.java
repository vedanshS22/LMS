package com.example.learnermanagementsystem.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class AssignLearnersToCohortRequest {

    @NotEmpty(message = "At least one learner is required")
    @Valid
    private List<CreateLearnerRequest> learners;

    public List<CreateLearnerRequest> getLearners() {
        return learners;
    }

    public void setLearners(List<CreateLearnerRequest> learners) {
        this.learners = learners;
    }
}
