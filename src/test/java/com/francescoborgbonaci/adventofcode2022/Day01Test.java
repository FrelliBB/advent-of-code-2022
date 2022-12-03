package com.francescoborgbonaci.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.francescoborgbonaci.adventofcode2022.Day01.ElvesData;
import static com.francescoborgbonaci.adventofcode2022.Day01.process;
import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void sampleData() {
        final List<String> input = List.of(
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

        final ElvesData result = process(input);
        assertThat(result.getTotalCaloriesHeldByTopNElves(1)).isEqualTo(24_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(2)).isEqualTo(35_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(3)).isEqualTo(45_000);
    }

}
