package com.infosys.insurai.service;

import com.infosys.insurai.entity.AgentAssignment;
import com.infosys.insurai.entity.User;
import com.infosys.insurai.entity.enums.AssignmentStatus;
import com.infosys.insurai.entity.enums.Role;
import com.infosys.insurai.entity.enums.Status;
import com.infosys.insurai.repository.AgentAssignmentRepository;
import com.infosys.insurai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AgentAssignmentRepository assignmentRepository;

    public UserService(UserRepository userRepository,
                       AgentAssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    // Get all pending agents
    public List<User> getPendingAgents() {
        return userRepository.findByRoleAndStatus(Role.AGENT, Status.PENDING);
    }

    // Approve agent
    public User approveAgent(Long agentId) {
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        agent.setStatus(Status.APPROVED);
        return userRepository.save(agent);
    }

    // Reject agent
    public User rejectAgent(Long agentId) {
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        agent.setStatus(Status.REJECTED);
        return userRepository.save(agent);
    }

    // Assign agent to user
    public AgentAssignment assignAgentToUser(Long agentId, Long userId) {

        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        if (agent.getRole() != Role.AGENT || agent.getStatus() != Status.APPROVED) {
            throw new RuntimeException("Agent is not approved");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        assignmentRepository.findByUserAndStatus(user, AssignmentStatus.ACTIVE)
                .ifPresent(existing -> {
                    existing.setStatus(AssignmentStatus.CLOSED);
                    assignmentRepository.save(existing);
                });

        AgentAssignment assignment = new AgentAssignment();
        assignment.setAgent(agent);
        assignment.setUser(user);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setStatus(AssignmentStatus.ACTIVE);

        return assignmentRepository.save(assignment);
    }
}
