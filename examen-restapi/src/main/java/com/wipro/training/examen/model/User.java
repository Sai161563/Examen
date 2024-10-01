package com.wipro.training.examen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
	@SequenceGenerator(name="user_seq", initialValue = 101, allocationSize=1)
	@Column(name="uid")
	private Long id;

	@Column(name="fullName")
	private String fname;

	@Column(unique=true)    // unique constraint
	private String email;

	@Column(name="Mobile", unique=true)
	private String mobileNo;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dob;

	@Column(name="City")
	private String city;

	@Column(name="Qualification")
	private String qualification;

	@Column(name="State")
	private String state;

	@Column(name="Year of Completion")
	private String year;

	@Column(name="password")
	private String password;
	
	@Column(name="reset_token")
    private String resetToken;

    @Column(name="token_expiry_date")
    private LocalDate tokenExpiryDate;
}