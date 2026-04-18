package com.example.learnermanagementsystem.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateLearnerRequest {

    @NotBlank(message = "Learner name is required")
    @Size(max = 255, message = "Learner name must be at most 255 characters")
    private String learnerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String learnerEmail;

    @NotBlank(message = "Phone number is required")
    @Size(min = 5, max = 32, message = "Phone must be between 5 and 32 characters")
    private String learnerPhone;

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
}
