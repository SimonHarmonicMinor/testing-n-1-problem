package com.example.demo.repository;

import com.example.demo.entity.Zoo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZooRepository extends JpaRepository<Zoo, Long> {
}