package io.github.frellibb.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void sampleData() {
        List<String> input = List.of(
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

        Day01.ElvesData result = new Day01().process(input);
        assertThat(result.getTotalCaloriesHeldByTopNElves(1)).isEqualTo(24_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(2)).isEqualTo(35_000);
        assertThat(result.getTotalCaloriesHeldByTopNElves(3)).isEqualTo(45_000);
    }

}
