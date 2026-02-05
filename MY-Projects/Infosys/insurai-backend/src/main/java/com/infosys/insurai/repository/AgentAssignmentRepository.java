package com.infosys.insurai.repository;

import com.infosys.insurai.entity.AgentAssignment;
import com.infosys.insurai.entity.User;
import com.infosys.insurai.entity.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentAssignmentRepository extends JpaRepository<AgentAssignment, Long> {

    Optional<AgentAssignment> findByUserAndStatus(User user, AssignmentStatus status);
}
