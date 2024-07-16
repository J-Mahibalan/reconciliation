package com.project.reconciliation.entities;

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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "subscriber")
public class Subscriber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="subscriber_id")
	private Long subscriberId;
	private String name;
	private String address;
	private String email;
}
