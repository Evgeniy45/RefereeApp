package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.Assignment;
import com.basketball.refereeapp.model.AssignmentStatus;
import com.basketball.refereeapp.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment createAssignment(Assignment assignment) {
        if (hasConflict(assignment)) {
            throw new IllegalStateException("Referee already assigned at this time.");
        }
        return assignmentRepository.save(assignment);
    }

    public Assignment updateStatus(Long id, AssignmentStatus status) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow();
        assignment.setStatus(status);
        return assignmentRepository.save(assignment);
    }



    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public boolean hasConflict(Assignment newAssignment) {
        List<Assignment> assignments = assignmentRepository.findAll();
        for (Assignment a : assignments) {
            if (a.getReferee().getId().equals(newAssignment.getReferee().getId()) &&
                    a.getMatch().getMatchDate().isEqual(newAssignment.getMatch().getMatchDate())) {
                return true;
            }
        }
        return false;
    }

}
