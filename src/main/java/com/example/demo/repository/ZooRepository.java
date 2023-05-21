package com.example.demo.repository;

import com.example.demo.entity.Zoo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZooRepository extends JpaRepository<Zoo, Long> {
    @Query("FROM Zoo z LEFT JOIN FETCH z.animals")
    List<Zoo> findAllWithAnimalsJoined();
}