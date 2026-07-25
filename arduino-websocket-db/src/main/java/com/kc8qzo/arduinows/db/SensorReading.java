package com.kc8qzo.arduinows.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "SensorReading")
public class SensorReading {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "PostedAt", nullable = false)
	private Instant postedAt;

	protected SensorReading() {
	}

	public SensorReading(Instant postedAt) {
		this.postedAt = postedAt;
	}

	public Long getId() {
		return id;
	}

	public Instant getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Instant postedAt) {
		this.postedAt = postedAt;
	}
}
