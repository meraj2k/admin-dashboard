package com.admindashboard.admindashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admindashboard.admindashboard.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}