package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.document.StoreServiceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreServiceLogRepository extends MongoRepository<StoreServiceLog, String> {
}