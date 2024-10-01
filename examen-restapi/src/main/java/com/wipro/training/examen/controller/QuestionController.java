package com.wipro.training.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.training.examen.dto.ExamQuestionsDTO;
import com.wipro.training.examen.dto.QuestionDTO;
import com.wipro.training.examen.model.Question;
import com.wipro.training.examen.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @PostMapping("/questions")
    public ResponseEntity<Boolean> createQuestion(@RequestBody Question question) {
        questionService.createQuestion(question.getExam().getName(), question.getQuestion(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD(), question.getCorrectAnswer());
        return ResponseEntity.ok(true);
    }
    
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    
    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(true);
    }
    
    @GetMapping("/exams/{examId}/questions")
    public ResponseEntity<ExamQuestionsDTO> getQuestionsByExamId(@PathVariable Long examId) {
        List<Question> questions = questionService.getQuestionsByExamId(examId);
        ExamQuestionsDTO dto = new ExamQuestionsDTO();
        dto.setExamId(questions.get(0).getExam().getEId());
        dto.setExamName(questions.get(0).getExam().getName());
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(q -> {
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setQId(q.getQId());
                    questionDTO.setQuestion(q.getQuestion());
                    questionDTO.setOptionA(q.getOptionA());
                    questionDTO.setOptionB(q.getOptionB());
                    questionDTO.setOptionC(q.getOptionC());
                    questionDTO.setOptionD(q.getOptionD());
                    return questionDTO;
                })
                .collect(Collectors.toList());
        dto.setQuestions(questionDTOs);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/admin/exams/{examId}/questions")
    public ResponseEntity<ExamQuestionsDTO> getAdminQuestionsByExamId(@PathVariable Long examId) {
        List<Question> questions = questionService.getQuestionsByExamId(examId);
        ExamQuestionsDTO dto = new ExamQuestionsDTO();
        dto.setExamId(questions.get(0).getExam().getEId());
        dto.setExamName(questions.get(0).getExam().getName());
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(q -> {
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setQId(q.getQId());
                    questionDTO.setQuestion(q.getQuestion());
                    questionDTO.setOptionA(q.getOptionA());
                    questionDTO.setOptionB(q.getOptionB());
                    questionDTO.setOptionC(q.getOptionC());
                    questionDTO.setOptionD(q.getOptionD());
                    questionDTO.setCorrectOption(q.getCorrectAnswer());
                    return questionDTO;
                })
                .collect(Collectors.toList());
        dto.setQuestions(questionDTOs);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}