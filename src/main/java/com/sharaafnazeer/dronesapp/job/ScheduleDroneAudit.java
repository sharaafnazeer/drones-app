package com.sharaafnazeer.dronesapp.job;

import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.entities.AuditLog;
import com.sharaafnazeer.dronesapp.entities.DroneBatteryAudit;
import com.sharaafnazeer.dronesapp.enums.AuditType;
import com.sharaafnazeer.dronesapp.services.AuditLogService;
import com.sharaafnazeer.dronesapp.services.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class ScheduleDroneAudit {

    static final Logger logger = Logger.getLogger(ScheduleDroneAudit.class.getName());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DroneService droneService;
    private final AuditLogService auditLogService;

    @Autowired
    public ScheduleDroneAudit(DroneService droneService, AuditLogService auditLogService) {
        this.droneService = droneService;
        this.auditLogService = auditLogService;
    }

    @Scheduled(fixedRateString = "${job.interval}")
    @Async
    public void reportCurrentTime() {
        logger.info("Checking Drone Capacity " + dateFormat.format(new Date()));

        AuditLog auditLog = new AuditLog();
        auditLog.setTitle("Checking Drone Capacity");
        auditLog.setCreatedAt(LocalDateTime.now());
        auditLog.setAuditType(AuditType.BATTERY_LOG);

        List<DroneDto> drones = droneService.getDrones();
        Set<DroneBatteryAudit> audits = new HashSet<>();
        drones.forEach(drone -> {
            logger.info("Drone Serial Number - " + drone.getSerialNumber() + ", Battery Level - " + drone.getBatteryLife());
            DroneBatteryAudit droneBatteryAudit = new DroneBatteryAudit();
            droneBatteryAudit.setAuditLog(auditLog);
            droneBatteryAudit.setSerialNumber(drone.getSerialNumber());
            droneBatteryAudit.setBatteryLife(drone.getBatteryLife());
            audits.add(droneBatteryAudit);
        });
        auditLog.setDroneBatteryAudits(audits);
        auditLogService.saveAuditLog(auditLog);
    }
}
