package com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.repository;

import com.kojstarinnovations.terminal.st.infrastructure.adapters.output.persistence.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}