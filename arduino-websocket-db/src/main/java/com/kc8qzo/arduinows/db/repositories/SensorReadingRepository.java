package com.kc8qzo.arduinows.db.repositories;

import com.kc8qzo.arduinows.db.domain.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    Optional<SensorReading> findFirstByOrderByIdDesc();
}
