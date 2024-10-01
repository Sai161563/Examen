package com.wipro.training.examen.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamSubmissionDTO {
    private Long userId;
    private String name; // Only subject is passed now
    private Map<Long, String> answers; // Question ID -> Selected Option

    // Getters and Setters
}