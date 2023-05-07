package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.entities.AuditLog;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.entities.DroneBatteryAudit;
import com.sharaafnazeer.dronesapp.enums.AuditType;
import com.sharaafnazeer.dronesapp.repos.AuditLogRepository;
import com.sharaafnazeer.dronesapp.repos.DroneBatteryAuditRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuditLogServiceImplTest {
    @InjectMocks
    AuditLogServiceImpl auditLogService;
    @Mock
    AuditLogRepository auditLogRepository;
    @Mock
    DroneBatteryAuditRepository droneBatteryAuditRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveAuditLog() {
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(getMockAuditLog());
        when(droneBatteryAuditRepository.saveAll(getMockAuditLog().getDroneBatteryAudits()))
                .thenReturn(List.of(getMockAuditLog().getDroneBatteryAudits().toArray(new DroneBatteryAudit[0])));
        AuditLog auditLog = auditLogService.saveAuditLog(getMockAuditLog());
        assertNotNull(auditLog);
        assertEquals(1, auditLog.getId());
        assertEquals(1, auditLog.getDroneBatteryAudits().size());
    }

    AuditLog getMockAuditLog() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId(1);
        auditLog.setAuditType(AuditType.BATTERY_LOG);
        auditLog.setTitle("Logging Battery Lifetime");
        auditLog.setCreatedAt(LocalDateTime.now());

        DroneBatteryAudit droneBatteryAudit = new DroneBatteryAudit();
        droneBatteryAudit.setId(1);
        droneBatteryAudit.setBatteryLife(80);
        droneBatteryAudit.setSerialNumber("ABC123");
        droneBatteryAudit.setAuditLog(auditLog);

        auditLog.setDroneBatteryAudits(Set.of(droneBatteryAudit));
        return auditLog;
    }
}