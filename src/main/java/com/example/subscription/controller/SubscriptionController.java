package com.example.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.subscription.entity.Subscription;
import com.example.subscription.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;

	@PostMapping("/subscribe")
	public void subscribe(@RequestBody Subscription subscription) {

		subscriptionService.subscribe(subscription);
	}

	@PostMapping("/unsubscribe")
	public void unsubscribe(@RequestBody Subscription subscription) {

		subscriptionService.unsubscribe(subscription);
	}

	@GetMapping("/get")
	public List<Subscription> findBySubscriber(@RequestParam String subscriberName) {
		return subscriptionService.findBySubscriber(subscriberName);
	}

	@GetMapping("/availableBooks")
	public String findAvailableBooks() {
		return subscriptionService.findAvailableBooks();
	}
}
