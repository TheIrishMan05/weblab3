package org.example.labwork3.models;


import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "x", nullable = false)
    private double x;
    @Column(name = "y", nullable = false)
    private double y;
    @Column(name = "r", nullable = false)
    private double r;
    @Column(name = "is_hit", nullable = false)
    private boolean hit;
    @Column(name = "time", nullable = false)
    private String time;
    @Column(name = "execution_time", nullable = false)
    private long executionTime;
    @Column(name = "session_id")
    private String sessionId;
}
