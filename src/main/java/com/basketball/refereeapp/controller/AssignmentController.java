package com.basketball.refereeapp.controller;

import com.basketball.refereeapp.model.Assignment;
import com.basketball.refereeapp.model.AssignmentStatus;
import com.basketball.refereeapp.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAll() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping
    public Assignment create(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping("/{id}")
    public Assignment getById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }
    @PutMapping("/{id}/status")
    public Assignment updateStatus(@PathVariable Long id, @RequestParam AssignmentStatus status) {
        return assignmentService.updateStatus(id, status);
    }

}
