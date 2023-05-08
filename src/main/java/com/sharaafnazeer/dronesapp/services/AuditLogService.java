/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.entities.AuditLog;

public interface AuditLogService {
    AuditLog saveAuditLog(AuditLog auditLog);
}
