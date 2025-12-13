package com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.us.infrastructure.adapters.output.persistence.document.UserServiceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceLogRepository extends MongoRepository<UserServiceLog, String> {
}