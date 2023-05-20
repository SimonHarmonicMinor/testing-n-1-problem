package com.example.demo.dto;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Zoo;

import java.util.List;

public record ZooResponse(
    Long id,
    String name,
    List<AnimalResponse> animals
) {
    public ZooResponse(Zoo zoo) {
        this(zoo.getId(), zoo.getName(), zoo.getAnimals().stream().map(AnimalResponse::new).toList());
    }

    public record AnimalResponse(Long id, String name) {
        public AnimalResponse(Animal animal) {
            this(animal.getId(), animal.getName());
        }
    }
}
