package com.dev.wetterstation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	
	@Autowired
	EventRepository repository;
	
    @GetMapping("/events")
    public List<Event> getEvents() {
    	List<Event> events = repository.findAll();
    	return events;
    }
}