package com.cloud.security.is.user.api.log;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */

public interface AuditLogRespository extends CrudRepository<AuditLog,Long> {


}
