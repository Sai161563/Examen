package com.wipro.training.examen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.training.examen.dto.ExamSubmissionDTO;
import com.wipro.training.examen.dto.UserReportDTO;
import com.wipro.training.examen.model.Exam;
import com.wipro.training.examen.model.Question;
import com.wipro.training.examen.model.Report;
import com.wipro.training.examen.model.User;
import com.wipro.training.examen.repository.ExamRepository;
import com.wipro.training.examen.repository.QuestionRepository;
import com.wipro.training.examen.repository.ReportRepository;
import com.wipro.training.examen.repository.UserRepository;

@Service
public class ReportService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;

    public Report generateReport(ExamSubmissionDTO examSubmission) {
        User user = userRepository.findById(examSubmission.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Exam> exam = examRepository.findByName(examSubmission.getName());
        if (exam == null) {
            throw new RuntimeException("Exam not found for subject");
        }

        int correctAnswers = 0;

        // Iterate through the submitted answers
        for (Map.Entry<Long, String> entry : examSubmission.getAnswers().entrySet()) {
            Long questionId = entry.getKey();
            String selectedAnswer = entry.getValue();

            // Fetch the question from the database
            Question question = questionRepository.findById(questionId)
                                .orElseThrow(() -> new RuntimeException("Question not found: " + questionId));

            // Compare the selected answer with the correct answer
            if (question.getCorrectAnswer().equals(selectedAnswer)) {
                correctAnswers++;
            }
        }

        // Calculate the user's total marks (assuming each question is worth 1 point)
        int totalMarks = correctAnswers;

        // Create and save the report
        Report report = new Report();
        report.setUser(user);
        report.setExam(exam.get());
        report.setTotalMarks(totalMarks);
        reportRepository.save(report);

        return report;
    }
    
    public List<Report> getAllReports(){
    	return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
    
    public List<UserReportDTO> getUserReportDTO(Long id) {
        // Retrieve the report from the report table based on the user id
        List<Report> reports = reportRepository.findAllByUserId(id);
        List<UserReportDTO> userReports = new ArrayList<UserReportDTO>();

        // Create a new UserReportDTO object and populate it with the retrieved data
        for(Report report:reports) {
        	UserReportDTO userReportDTO = new UserReportDTO();
        	userReportDTO.setName(report.getExam().getName());
        	userReportDTO.setTotalMarks(report.getTotalMarks());
        	userReports.add(userReportDTO);
        }
        return userReports;
    }
}