package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.Assignment;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.model.User;
import com.basketball.refereeapp.repository.AssignmentRepository;
import com.basketball.refereeapp.repository.MatchRepository;
import com.basketball.refereeapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private MatchRepository matchRepository; // додано

    @Mock
    private UserRepository userRepository; // додано

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void testCreateAssignment() {
        // Створюємо мокові об'єкти
        Match match = new Match();
        match.setId(1L);

        User referee = new User();
        referee.setId(2L);

        Assignment assignment = new Assignment();
        assignment.setMatch(match);
        assignment.setReferee(referee);

        // Підготовка моків
        when(assignmentRepository.findAll()).thenReturn(List.of());
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(userRepository.findById(2L)).thenReturn(Optional.of(referee));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);

        // Виконання і перевірка
        Assignment result = assignmentService.createAssignment(assignment);
        assertEquals(assignment, result);
    }
}
