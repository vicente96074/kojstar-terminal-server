package com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.auths.infrastructure.adapters.output.persistence.document.AuthServiceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthServiceInfoRepository extends MongoRepository<AuthServiceInfo, String> {
}