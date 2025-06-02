package com.basketball.refereeapp.controller;

import com.basketball.refereeapp.model.Assignment;
import com.basketball.refereeapp.model.AssignmentStatus;
import com.basketball.refereeapp.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Assignment Controller", description = "Керування призначеннями арбітрів на матчі")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Operation(summary = "Отримати всі призначення")
    @ApiResponse(responseCode = "200", description = "Список призначень успішно отримано")
    @GetMapping
    public List<Assignment> getAll() {
        return assignmentService.getAllAssignments();
    }

    @Operation(summary = "Створити нове призначення")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Призначення створено"),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані (наприклад, неіснуючий referee або match)"),
            @ApiResponse(responseCode = "409", description = "Арбітр уже призначений на цей час")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Assignment assignment) {
        try {
            return ResponseEntity.status(201).body(assignmentService.createAssignment(assignment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @Operation(summary = "Отримати призначення за ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Призначення знайдено"),
            @ApiResponse(responseCode = "404", description = "Призначення не знайдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Оновити статус призначення")
    @PutMapping("/{id}/status")
    public Assignment updateStatus(@PathVariable Long id, @RequestParam AssignmentStatus status) {
        return assignmentService.updateStatus(id, status);
    }
}
