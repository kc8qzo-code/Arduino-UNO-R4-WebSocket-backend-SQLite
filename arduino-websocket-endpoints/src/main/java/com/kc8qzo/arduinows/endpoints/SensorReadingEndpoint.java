package com.kc8qzo.arduinows.endpoints;

import com.kc8qzo.arduinows.contracts.dto.SensorReadingDTO;
import com.kc8qzo.arduinows.services.SensorReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SensorReadingEndpoint {

    private final SensorReadingService sensorReadingService;

    @MessageMapping("/sensor-reading")
    @SendTo("/topic/sensor-reading")
    public SensorReadingDTO createSensorReading(SensorReadingDTO sensorReadingDTO) {
        return sensorReadingService.receiveSensorReading(sensorReadingDTO);
    }

    @MessageMapping("/read-sensor-reading")
    @SendTo("/topic/read-sensor-reading")
    public SensorReadingDTO readSensorReading() {
        return sensorReadingService.readSensorReading();
    }
}
