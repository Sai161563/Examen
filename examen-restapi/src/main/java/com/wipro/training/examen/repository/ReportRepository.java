package com.wipro.training.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.wipro.training.examen.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{
	
	List<Report> findAllByUserId(@Param("id") Long id);
}
