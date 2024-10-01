package com.wipro.training.examen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ques_seq")
	@SequenceGenerator(name="ques_seq", initialValue = 1001, allocationSize=1)
	 private Long qId;

    private String question;

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer; // Stores the correct option (e.g., "A", "B", etc.)

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference
    private Exam exam;
}
