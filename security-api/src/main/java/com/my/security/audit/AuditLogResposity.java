package com.my.security.audit;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogResposity extends JpaSpecificationExecutor<AuditLog>,CrudRepository<AuditLog, Long>{

}
