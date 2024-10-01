package com.wipro.training.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.training.examen.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	List<Question> findByExam_eId(Long examId);
}
