package com.wipro.training.examen.dto;

import lombok.Data;

@Data
public class QuestionDTO {

	private Long qId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
}
