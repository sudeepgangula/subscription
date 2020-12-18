package com.example.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.subscription.entity.Subscription;
import com.example.subscription.service.SubscriptionService;

@RestController
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	private RestTemplate restTemplate;


	@PostMapping("/subscriptions")
	public ResponseEntity<?> subscribe(@RequestBody Subscription subscription) {

		String findBookUrl = "http://localhost:8081/books?bookId=" + subscription.getBookId();

		ResponseEntity<String> reponseEntity = restTemplate.exchange(findBookUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				});

		if (null != reponseEntity.getBody()) {
			return new ResponseEntity<>("book copies not available for subscription", HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			subscriptionService.subscribe(subscription);
			return new ResponseEntity<>("Successful creation of subscription record", HttpStatus.OK);
		}


	}

	@PostMapping("/unsubscribe")
	public void unsubscribe(@RequestBody Subscription subscription) {

		subscriptionService.unsubscribe(subscription);
	}

	@GetMapping("/subscriptions")
	public List<Subscription> findBySubscriber(@RequestParam String subscriberName) {
		return subscriptionService.findBySubscriber(subscriberName);
	}

	@GetMapping("/availableBooks")
	public String findAvailableBooks() {
		return subscriptionService.findAvailableBooks();
	}
}
