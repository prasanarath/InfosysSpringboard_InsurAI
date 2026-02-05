package com.infosys.insurai.controller;

import com.infosys.insurai.entity.User;
import com.infosys.insurai.entity.AgentAssignment;
import com.infosys.insurai.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // View all pending agents
    @GetMapping("/agents/pending")
    public List<User> getPendingAgents() {
        return userService.getPendingAgents();
    }

    // Approve agent
    @PutMapping("/agents/{id}/approve")
    public User approveAgent(@PathVariable Long id) {
        return userService.approveAgent(id);
    }

    // Reject agent
    @PutMapping("/agents/{id}/reject")
    public User rejectAgent(@PathVariable Long id) {
        return userService.rejectAgent(id);
    }

    @PostMapping("/assign-agent")
    public AgentAssignment assignAgentToUser(
            @RequestParam("agentId") Long agentId,
            @RequestParam("userId") Long userId) {

        return userService.assignAgentToUser(agentId, userId);
    }
}
