package com.kc8qzo.arduinows.endpoints;

import com.kc8qzo.arduinows.contracts.dto.SensorReadingDTO;
import com.kc8qzo.arduinows.services.SensorReadingService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SensorReadingEndpoint {

    private final SensorReadingService sensorReadingService;

    public SensorReadingEndpoint(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @MessageMapping("/sensor-readings")
    @SendTo("/topic/sensor-readings")
    public SensorReadingDTO save(SensorReadingDTO sensorReadingDTO) {
        return sensorReadingService.save(sensorReadingDTO);
    }
}
