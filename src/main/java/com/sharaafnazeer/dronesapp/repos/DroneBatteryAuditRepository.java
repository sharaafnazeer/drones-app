package com.sharaafnazeer.dronesapp.repos;

import com.sharaafnazeer.dronesapp.entities.DroneBatteryAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAudit, Integer> {
}
