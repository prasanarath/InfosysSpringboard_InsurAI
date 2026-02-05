package com.infosys.insurai.repository;

import com.infosys.insurai.entity.User;
import com.infosys.insurai.entity.enums.Role;
import com.infosys.insurai.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoleAndStatus(Role role, Status status);
}
