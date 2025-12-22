package com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.storage.infrastructure.adapters.output.persistence.document.StorageServiceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageServiceInfoRepository extends MongoRepository<StorageServiceInfo, String> {
}