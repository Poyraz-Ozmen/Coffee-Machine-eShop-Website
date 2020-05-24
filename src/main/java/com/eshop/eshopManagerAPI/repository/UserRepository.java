package com.eshop.eshopManagerAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	User findByid(String id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByid(long userId);
}