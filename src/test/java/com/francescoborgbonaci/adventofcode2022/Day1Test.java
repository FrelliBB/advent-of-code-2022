package com.francescoborgbonaci.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    @Test
    void sampleData() {
        final var input = List.of(
            "1000", "2000", "3000",
            "",
            "4000",
            "",
            "5000", "6000",
            "",
            "7000", "8000", "9000",
            "",
            "10000"
        );

        final var result = Day1.parse(input);
        assertThat(result.getTotalCaloriesHeldByElfWithMostCalories()).isEqualTo(24_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(1)).isEqualTo(24_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(2)).isEqualTo(35_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(3)).isEqualTo(45_000);
    }

}
