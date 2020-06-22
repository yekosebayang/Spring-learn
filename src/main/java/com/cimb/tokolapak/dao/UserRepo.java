package com.cimb.tokolapak.dao;

import com.cimb.tokolapak.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
}
