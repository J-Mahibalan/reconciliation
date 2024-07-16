package com.project.reconciliation.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.reconciliation.entities.Subscriber;

@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

}
