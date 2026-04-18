package com.example.learnermanagementsystem.DTO;

import java.util.ArrayList;
import java.util.List;

public class LearnerDTO {
    private Long learnerId;
    private String learnerName;
    private String learnerEmail;
    private String learnerPhone;
    private List<CohortSummaryDTO> cohorts = new ArrayList<>();

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getLearnerEmail() {
        return learnerEmail;
    }

    public void setLearnerEmail(String learnerEmail) {
        this.learnerEmail = learnerEmail;
    }

    public String getLearnerPhone() {
        return learnerPhone;
    }

    public void setLearnerPhone(String learnerPhone) {
        this.learnerPhone = learnerPhone;
    }

    public List<CohortSummaryDTO> getCohorts() {
        return cohorts;
    }

    public void setCohorts(List<CohortSummaryDTO> cohorts) {
        this.cohorts = cohorts != null ? cohorts : new ArrayList<>();
    }
}

