package com.example.learnermanagementsystem.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCohortRequest {

    @NotBlank(message = "Cohort name is required")
    @Size(max = 255, message = "Cohort name must be at most 255 characters")
    private String cohortName;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String cohortDescription;

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
}
