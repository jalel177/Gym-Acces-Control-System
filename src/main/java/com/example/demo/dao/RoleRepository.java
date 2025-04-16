package com.example.demo.dao;

import com.example.demo.model.Role;
import com.example.demo.model.Rolename;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r FROM Role r WHERE r.Rolename = :rolename")
    Optional<Role> findByRolename(@Param("rolename") Rolename rolename);
}
