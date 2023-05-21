package com.example.demo.testutil;

import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.With;
import lombok.experimental.UtilityClass;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UtilityClass
public class QueryCountAssertions {
    @SneakyThrows
    public static <T> T assertQueryCount(Supplier<T> supplier, Expectation expectation) {
        QueryCountService.clear();
        final var result = supplier.get();
        final var queryCount = QueryCountService.get();
        assertAll(
            () -> {
                if (expectation.selects >= 0) {
                    assertEquals(expectation.selects, queryCount.getSelect(), "Unexpected selects count");
                }
            },
            () -> {
                if (expectation.inserts >= 0) {
                    assertEquals(expectation.inserts, queryCount.getInsert(), "Unexpected inserts count");
                }
            },
            () -> {
                if (expectation.deletes >= 0) {
                    assertEquals(expectation.deletes, queryCount.getDelete(), "Unexpected deletes count");
                }
            },
            () -> {
                if (expectation.updates >= 0) {
                    assertEquals(expectation.updates, queryCount.getUpdate(), "Unexpected updates count");
                }
            }
        );
        return result;
    }


    @With
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Expectation {
        private int selects = -1;
        private int inserts = -1;
        private int deletes = -1;
        private int updates = -1;

        public static Expectation ofSelects(int selects) {
            return new Expectation().withSelects(selects);
        }

        public static Expectation ofInserts(int inserts) {
            return new Expectation().withInserts(inserts);
        }

        public static Expectation ofDeletes(int deletes) {
            return new Expectation().withDeletes(deletes);
        }

        public static Expectation ofUpdates(int updates) {
            return new Expectation().withUpdates(updates);
        }
    }
}
