package com.kc8qzo.arduinows.db.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "arduino_sensor")
public class SensorReading {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // works in SQLite
	@Column(name = "id")
	private Long id;

	@Column(name = "temperature")
	private double temperature;

	@Column(name = "humidity")
	private double humidity;

	@Column(name = "light")
	private Integer light;

	public Integer getMyInteger() {

		return light;
	}

	public void setMyInteger(Integer light) {

		this.light = light;
	}

	@Column(name = "pass_value")
	private long passValue;

	@Column(name = "sent_at")
	private Instant sentAt;

	@Column(name = "posted_at", nullable = false)
	private Instant postedAt;
}
