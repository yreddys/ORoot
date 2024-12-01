package com.root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.root.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	boolean existsByEmail(String email);

	Users findByEmail(String email);

}
