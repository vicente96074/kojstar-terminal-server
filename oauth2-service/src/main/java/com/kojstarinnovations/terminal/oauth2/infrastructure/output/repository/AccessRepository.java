package com.kojstarinnovations.terminal.oauth2.infrastructure.output.repository;

import com.kojstarinnovations.terminal.commons.data.enums.AccessName;
import com.kojstarinnovations.terminal.oauth2.infrastructure.output.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, String> {
    List<Access> findByAccessNameIn(@Param("accessNames") List<AccessName> accessNames);
}