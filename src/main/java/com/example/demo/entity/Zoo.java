package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "zoo")
@Getter
@Setter
public class Zoo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "zoo", cascade = PERSIST)
    private List<Animal> animals = new ArrayList<>();

    public void addAnimal(String name) {
        final var animal = new Animal();
        animal.setName(name);
        animal.setZoo(this);
        animals.add(animal);
    }
}
