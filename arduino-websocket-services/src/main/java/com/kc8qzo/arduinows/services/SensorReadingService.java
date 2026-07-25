package com.kc8qzo.arduinows.services;

import com.kc8qzo.arduinows.contracts.dto.SensorReadingDTO;
import com.kc8qzo.arduinows.db.domain.SensorReading;
import com.kc8qzo.arduinows.db.repositories.SensorReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    @Transactional
    public SensorReadingDTO save(SensorReadingDTO sensorReadingDTO) {
        SensorReading sensorReading = new SensorReading();
        sensorReading.setTemperature(sensorReadingDTO.getTemperature());
        sensorReading.setHumidity(sensorReadingDTO.getHumidity());
        sensorReading.setLight(sensorReadingDTO.getLight());
        sensorReading.setPassValue(sensorReadingDTO.getPassValue());
        sensorReading.setSentAt(sensorReadingDTO.getSentAt());
        sensorReading.setPostedAt(
                sensorReadingDTO.getPostedAt() == null ? Instant.now() : sensorReadingDTO.getPostedAt()
        );

        SensorReading savedReading = sensorReadingRepository.save(sensorReading);

        sensorReadingDTO.setId(savedReading.getId());
        sensorReadingDTO.setPostedAt(savedReading.getPostedAt());
        return sensorReadingDTO;
    }
}
