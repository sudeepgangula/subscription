package com.example.subscription.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.subscription.entity.Subscription;
import com.example.subscription.repository.SubscriptionRepository;

@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public void subscribe(Subscription subscription) {

		subscription.setDateSubscribed(LocalDate.now());
		subscriptionRepository.save(subscription);

		String serviceURL = "http://localhost:8081/books/allocateBook?bookId="+subscription.getBookId();

		ResponseEntity<List<Address>> reponseEntity = restTemplate.exchange(serviceURL, HttpMethod.POST, null,
				new ParameterizedTypeReference<List<Address>>() {
				});

	}

	public void unsubscribe(Subscription subscription) {
		subscription = subscriptionRepository.findBySubscriberNameAndBookId(subscription.getSubscriberName(),
				subscription.getBookId());
		subscription.setDateReturned(LocalDate.now());

		subscriptionRepository.save(subscription);
		String serviceURL = "http://localhost:8081/books/deallocateBook?bookId=" + subscription.getBookId();

		ResponseEntity<List<Address>> reponseEntity = restTemplate.exchange(serviceURL, HttpMethod.POST, null,
				new ParameterizedTypeReference<List<Address>>() {
				});
	}

	public List<Subscription> findBySubscriber(String subscriberName) {
		return subscriptionRepository.findBySubscriberName(subscriberName);
	}

	public String findAvailableBooks() {
		String serviceURL = "http://localhost:8081/books/availableBooks";

		ResponseEntity<String> reponseEntity = restTemplate.exchange(serviceURL, HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				});
		
		return reponseEntity.getBody();
	}

}
