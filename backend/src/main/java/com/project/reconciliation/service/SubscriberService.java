package com.project.reconciliation.service;

import java.util.List;

import com.project.reconciliation.dto.Bill;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.exceptions.SubscriberNotFoundException;

public interface SubscriberService {
	public Subscriber getSubscriberById(Long subscriberId)throws SubscriberNotFoundException;

	public List<Bill> getBillingDetailsBySubscriberId(Long subscriberId) throws SubscriberNotFoundException;
}
