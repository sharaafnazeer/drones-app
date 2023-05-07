package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.entities.AuditLog;
import com.sharaafnazeer.dronesapp.repos.AuditLogRepository;
import com.sharaafnazeer.dronesapp.repos.DroneBatteryAuditRepository;
import com.sharaafnazeer.dronesapp.services.AuditLogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final DroneBatteryAuditRepository droneBatteryAuditRepository;

    @Autowired
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, DroneBatteryAuditRepository droneBatteryAuditRepository) {
        this.auditLogRepository = auditLogRepository;
        this.droneBatteryAuditRepository = droneBatteryAuditRepository;
    }

    @Override
    public AuditLog saveAuditLog(AuditLog auditLog) {
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        if (auditLog.getDroneBatteryAudits().size() > 0)
            droneBatteryAuditRepository.saveAll(auditLog.getDroneBatteryAudits());

        return savedAuditLog;
    }
}
