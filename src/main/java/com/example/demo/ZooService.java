package com.example.demo;

import com.example.demo.dto.ZooResponse;
import com.example.demo.repository.ZooRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZooService {
    private final ZooRepository zooRepository;

    @Transactional(readOnly = true)
    public List<ZooResponse> findAllZoos() {
        final var zoos = zooRepository.findAllWithAnimalsJoined();
        return zoos.stream()
                   .map(ZooResponse::new)
                   .toList();
    }
}
