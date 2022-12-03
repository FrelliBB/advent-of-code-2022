package com.francescoborgbonaci.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    @Test
    void sampleData() {
        final var input = List.of("A Y", "B X", "C Z");
        final var result = Day2.process(input);
        assertThat(result.part1Score()).isEqualTo(15);
        assertThat(result.part2Score()).isEqualTo(12);
    }

}