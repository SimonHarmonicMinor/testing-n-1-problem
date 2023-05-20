package com.example.dto;

import java.util.List;

public record ZooResponse(
    Long id,
    String name,
    List<AnimalResponse> animals
) {
    public record AnimalResponse(Long id, String name) {
    }
}
