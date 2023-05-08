/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.entities;

import com.sharaafnazeer.dronesapp.enums.AuditType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "type", length = 30)
    @Enumerated(value = EnumType.STRING)
    private AuditType auditType;

    @OneToMany(mappedBy = "auditLog")
    private Set<DroneBatteryAudit> droneBatteryAudits;

    private LocalDateTime createdAt;
}
