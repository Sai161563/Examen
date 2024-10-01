package com.wipro.training.examen.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExamQuestionsDTO {
    private Long examId;
    private String examName;
    private List<QuestionDTO> questions;
}
