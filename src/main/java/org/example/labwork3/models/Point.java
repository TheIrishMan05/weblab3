package org.example.labwork3.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private double x;

    private double y;

    private double r;

    private boolean hit;

    private String time;

    private long executionTime;

    private String sessionId;
}
