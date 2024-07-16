package com.project.reconciliation.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
@ToString
@Table(name = "data_usage_cost")
public class DataUsageCost {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer costId;
    @ManyToOne
    @JoinColumn(name="subscriber_id")
    private Subscriber subscriber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer data;
    private Integer costPerGB;
    private Double dataCost;
}
