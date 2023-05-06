package com.sharaafnazeer.dronesapp.job;

import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.services.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@Slf4j
public class ScheduleDroneAudit {

    static final Logger logger = Logger.getLogger(ScheduleDroneAudit.class.getName());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final DroneService droneService;

    @Autowired
    public ScheduleDroneAudit(DroneService droneService) {
        this.droneService = droneService;
    }

    @Scheduled(fixedRateString = "${job.interval}")
    public void reportCurrentTime() {
        logger.info("Checking Drone Capacity " + dateFormat.format(new Date()));
        List<DroneDto> drones = droneService.getDrones();
        drones.forEach(drone -> {
            logger.info("Drone Serial Number - " + drone.getSerialNumber() + ", Battery Level - " + drone.getBatteryLife());
        });
    }
}
