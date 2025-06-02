package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.Assignment;
import com.basketball.refereeapp.model.AssignmentStatus;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.model.User;
import com.basketball.refereeapp.repository.AssignmentRepository;
import com.basketball.refereeapp.repository.MatchRepository;
import com.basketball.refereeapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public AssignmentService(
            AssignmentRepository assignmentRepository,
            MatchRepository matchRepository,
            UserRepository userRepository
    ) {
        this.assignmentRepository = assignmentRepository;
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment createAssignment(Assignment assignment) {
        Long matchId = assignment.getMatch().getId();
        Long refereeId = assignment.getReferee().getId();

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match with ID " + matchId + " not found"));

        User referee = userRepository.findById(refereeId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + refereeId + " not found"));

        assignment.setMatch(match);
        assignment.setReferee(referee);

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
