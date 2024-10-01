package com.wipro.training.examen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminReportDTO {

	private String fname;
	private String examName;
	private Integer totalMarks;
	private String email;
	private String mobileNo;
	private String city;
	private String state;
}
