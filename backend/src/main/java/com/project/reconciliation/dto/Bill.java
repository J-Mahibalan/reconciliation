package com.project.reconciliation.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bill {
	private String name;
	private String email;
	private Double billingAmount;
	private String billingPeriod;
	private LocalDate dueDate;
}
