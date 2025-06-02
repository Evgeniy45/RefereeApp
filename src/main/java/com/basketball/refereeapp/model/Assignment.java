package com.basketball.refereeapp.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "referee_id")
    private User referee;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;


}
