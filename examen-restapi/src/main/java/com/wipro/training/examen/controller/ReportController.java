package com.wipro.training.examen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.examen.dto.AdminReportDTO;
import com.wipro.training.examen.dto.ExamSubmissionDTO;
import com.wipro.training.examen.dto.ReportDTO;
import com.wipro.training.examen.dto.UserReportDTO;
import com.wipro.training.examen.model.Report;
import com.wipro.training.examen.model.User;
import com.wipro.training.examen.service.ReportService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@PostMapping("/submit-exam")
	public ResponseEntity<ReportDTO> submitExam(@RequestBody ExamSubmissionDTO examSubmission) {
		try {
			// Generate the report for the user based on the submitted answers
			Report report = reportService.generateReport(examSubmission);

			User user=report.getUser();

			ReportDTO reportDTO = new ReportDTO(report.getId(),report.getTotalMarks(),
					user.getFname(),report.getExam().getName());

			// Return the report as a response
			return ResponseEntity.ok(reportDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/reports")
	public ResponseEntity<List<AdminReportDTO>> getAllReports() {
	    List<Report> reports = reportService.getAllReports();
	    List<AdminReportDTO> adminReports = new ArrayList<>();

	    for (Report report : reports) {
	        User user = report.getUser();
	        AdminReportDTO adminReportDTO = new AdminReportDTO(user.getFname(),report.getExam().getName(),
	                report.getTotalMarks(),user.getEmail(),user.getMobileNo(),user.getCity(),user.getState());
	        adminReports.add(adminReportDTO);
	    }

	    return new ResponseEntity<>(adminReports, HttpStatus.OK);
	}

	@GetMapping("/reports/{id}")
	public ResponseEntity<AdminReportDTO> getReportById(@PathVariable Long id) {
		try {

			Report report = reportService.getReportById(id);

			User user=report.getUser();

			AdminReportDTO adminReportDTO = new AdminReportDTO(user.getFname(),report.getExam().getName(),
					report.getTotalMarks(),user.getEmail(),user.getMobileNo(),user.getCity(),user.getState());

			return ResponseEntity.ok(adminReportDTO);
		}
	 catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	}
	
	@GetMapping("/reports/user/{id}")
    public ResponseEntity<List<UserReportDTO>> getUserReport(@PathVariable Long id) {
        List<UserReportDTO> userReportDTO = reportService.getUserReportDTO(id);
        return ResponseEntity.ok(userReportDTO);
    }
}
