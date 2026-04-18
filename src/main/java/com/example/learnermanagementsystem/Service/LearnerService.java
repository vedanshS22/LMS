package com.example.learnermanagementsystem.Service;

import com.example.learnermanagementsystem.DTO.LearnerDTO;
import com.example.learnermanagementsystem.DTO.request.CreateLearnerRequest;
import com.example.learnermanagementsystem.Exception.DuplicateEmailException;
import com.example.learnermanagementsystem.Exception.LearnerNotFoundException;
import com.example.learnermanagementsystem.Entity.Learner;
import com.example.learnermanagementsystem.Repository.LearnerRepository;
import com.example.learnermanagementsystem.mapper.LearnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Transactional
    public LearnerDTO createLearner(CreateLearnerRequest request) {
        String email = request.getLearnerEmail().trim().toLowerCase();
        if (learnerRepository.existsByLearnerEmail(email)) {
            throw new DuplicateEmailException("A learner with this email already exists");
        }
        Learner saved = learnerRepository.save(LearnerMapper.toEntity(request));
        return learnerRepository.findWithCohortsById(saved.getLearnerId())
                .map(LearnerMapper::toDto)
                .orElseGet(() -> LearnerMapper.toDto(saved));
    }

    @Transactional(readOnly = true)
    public LearnerDTO getLearnerById(Long learnerId) {
        return learnerRepository.findWithCohortsById(learnerId)
                .map(LearnerMapper::toDto)
                .orElseThrow(() -> new LearnerNotFoundException("Learner not found with id: " + learnerId));
    }

    @Transactional(readOnly = true)
    public List<LearnerDTO> listLearners(Optional<String> nameFilter) {
        if (nameFilter.isEmpty() || nameFilter.get().isBlank()) {
            return learnerRepository.findAllWithCohorts().stream()
                    .map(LearnerMapper::toDto)
                    .toList();
        }
        String name = nameFilter.get().trim();
        return learnerRepository.findAllWithCohortsByLearnerNameIgnoreCase(name).stream()
                .map(LearnerMapper::toDto)
                .toList();
    }
}
