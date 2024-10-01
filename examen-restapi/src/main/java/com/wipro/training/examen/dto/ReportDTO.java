package com.wipro.training.examen.dto;

//import com.wipro.training.examen.model.Exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO {

	private Long reportId;
	private Integer totalMarks;
	private String fname;
	private String examName;
}
