package com.wipro.training.examen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.training.examen.model.Exam;
import com.wipro.training.examen.model.Question;
import com.wipro.training.examen.repository.QuestionRepository;

@Service
public class QuestionService {
    
    @Autowired
    private ExamService examService;
    @Autowired
    private QuestionRepository questionRepository;
    
    public Question createQuestion(String examName, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        Exam exam = examService.getExamByName(examName);
        Question newQuestion = new Question();
        newQuestion.setExam(exam);
        newQuestion.setQuestion(question);
        newQuestion.setOptionA(optionA);
        newQuestion.setOptionB(optionB);
        newQuestion.setOptionC(optionC);
        newQuestion.setOptionD(optionD);
        newQuestion.setCorrectAnswer(correctAnswer);
        return questionRepository.save(newQuestion);
    }
    
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }
    
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    
    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExam_eId(examId);
    }
}