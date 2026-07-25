package com.kc8qzo.arduinows.services;

import com.kc8qzo.arduinows.contracts.dto.SensorReadingDTO;
import com.kc8qzo.arduinows.db.domain.SensorReading;
import com.kc8qzo.arduinows.db.repositories.SensorReadingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SensorReadingService {
    private final Clock clock;
    private final SensorReadingRepository sensorReadingRepository;
    private final ModelMapper mapper;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    @Transactional
    public SensorReadingDTO save(SensorReadingDTO sensorReadingDTO) {
        if (sensorReadingDTO.getPostedAt() == null) {
            sensorReadingDTO.setPostedAt(Instant.now(clock));
        }
        SensorReading entity = mapper.map(sensorReadingDTO, SensorReading.class);
        SensorReading savedReading = sensorReadingRepository.save(entity);
        return mapper.map(savedReading, SensorReadingDTO.class);
    }
}
