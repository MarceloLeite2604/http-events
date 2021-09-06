package com.github.marceloleite2604.httpevents.repository;

import com.github.marceloleite2604.httpevents.model.ExecutionContextPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExecutionContextRepository extends JpaRepository<ExecutionContextPo, UUID> {
}
