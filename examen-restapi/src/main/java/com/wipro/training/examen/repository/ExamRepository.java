package com.wipro.training.examen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.training.examen.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByName(String name);
}
