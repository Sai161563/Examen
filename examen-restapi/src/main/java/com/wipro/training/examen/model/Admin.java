package com.wipro.training.examen.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="admin")
public class Admin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="adminId")
    private Long id;
	
	@Column(unique=true)    // unique constraint
    private String email;
	
	@Column(name="password")
    private String password;
	
	@Column(name="reset_token")
    private String resetToken;

    @Column(name="token_expiry_date")
    private LocalDate tokenExpiryDate;

}
