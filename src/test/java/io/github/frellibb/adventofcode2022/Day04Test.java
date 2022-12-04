package io.github.frellibb.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    private final Day04 day = new Day04();

    @Test
    void sampleData() {
        final var lines = List.of(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
        );

        final var result = day.process(lines);
        assertThat(result.part1()).isEqualTo(2);
        assertThat(result.part2()).isEqualTo(4);
    }

}