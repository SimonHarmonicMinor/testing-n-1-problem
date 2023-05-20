package com.example.demo;

import com.example.demo.dto.ZooResponse;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.ZooRepository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@Import(ZooService.class)
class ZooServiceTest {
    @Autowired
    private ZooService zooService;
    @Autowired
    private ZooRepository zooRepository;

    @Test
    void shouldReturnAllZoos() {
        final var zoo1 = new Zoo();
        zoo1.setName("zoo1");
        zoo1.addAnimal("animal1");
        zoo1.addAnimal("animal2");

        final var zoo2 = new Zoo();
        zoo2.setName("zoo2");
        zoo2.addAnimal("animal3");
        zoo2.addAnimal("animal4");

        zooRepository.saveAll(List.of(zoo1, zoo2));

        final var allZoos = zooService.findAllZoos();

        assertThat(
            allZoos, Matchers.allOf(
                Matchers.<ZooResponse>hasSize(2),
                containsInAnyOrder(
                    equalTo(
                        new ZooResponse(
                            1L,
                            "zoo1",
                            List.of(
                                new ZooResponse.AnimalResponse(1L, "animal1"),
                                new ZooResponse.AnimalResponse(2L, "animal2")
                            )
                        )
                    ),
                    equalTo(
                        new ZooResponse(
                            2L,
                            "zoo2",
                            List.of(
                                new ZooResponse.AnimalResponse(3L, "animal3"),
                                new ZooResponse.AnimalResponse(4L, "animal4")
                            )
                        )
                    )
                )
            )
        );
    }
}