package com.example.demo;

import com.example.demo.dto.ZooResponse;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.ZooRepository;
import com.example.demo.testutil.DatasourceProxyBeanPostProcessor;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.example.demo.testutil.QueryCountAssertions.Expectation.ofSelects;
import static com.example.demo.testutil.QueryCountAssertions.assertQueryCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Transactional(propagation = NOT_SUPPORTED)
@Testcontainers
@Import({ZooService.class, DatasourceProxyBeanPostProcessor.class})
class ZooServiceTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

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

        final var allZoos = assertQueryCount(
            () -> zooService.findAllZoos(),
            ofSelects(1)
        );

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