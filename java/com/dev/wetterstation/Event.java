package com.dev.wetterstation;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class Event extends AbstractEntity {
	
	public Event() {
		this.created = LocalDateTime.now();
	}

	
	
	@Column
	private Float celsius;
	
	@Column
	private Float humidity;
	
	@Column
	private String recorded;
	
	@Column
	private LocalDateTime received;
	
	
	
	public Float getCelsius() {
		return celsius;
	}

	public Float getHumidity() {
		return humidity;
	}

	public String getRecorded() {
		return recorded;
	}

	public LocalDateTime getReceived() {
		return received;
	}

	
	
	public void setCelsius(Float celsius) {
		this.celsius = celsius;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public void setRecorded(String recorded) {
		this.recorded = recorded;
	}

	public void setReceived(LocalDateTime received) {
		this.received = received;
	}
	
	
}



