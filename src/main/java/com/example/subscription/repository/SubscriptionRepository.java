package com.example.subscription.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.subscription.entity.Subscription;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	@Query(value = "select * from Subscription where subscriber_name = :subscriberName", nativeQuery = true)
	List<Subscription> findBySubscriberName(String subscriberName);

	@Query(value = "select * from Subscription where subscriber_name = :subscriberName and book_id = :bookId", nativeQuery = true)
	Subscription findBySubscriberNameAndBookId(String subscriberName, Long bookId);
}
