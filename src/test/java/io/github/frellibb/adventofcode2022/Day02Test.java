package io.github.frellibb.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void sampleData() {
        List<String> input = List.of("A Y", "B X", "C Z");
        final var result = new Day02().process(input);

        assertThat(result.part1()).isEqualTo(15);
        assertThat(result.part2()).isEqualTo(12);
    }

}