package com.wipro.training.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.training.examen.model.Exam;
import com.wipro.training.examen.repository.ExamRepository;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository examRepository;
    
    public Exam createNewExam(String name) {
        Exam exam = new Exam();
        exam.setName(name);
        return examRepository.save(exam);
    }
    
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
    
    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElseThrow();
    }
    
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
    
    public Exam getExamByName(String name) {
        Optional<Exam> optionalExam = examRepository.findByName(name);
        return optionalExam.orElseThrow();
    }
}