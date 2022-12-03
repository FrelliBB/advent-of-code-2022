package io.github.frellibb.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.frellibb.adventofcode2022.Day02.Result;
import static io.github.frellibb.adventofcode2022.Day02.process;
import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void sampleData() {
        List<String> input = List.of("A Y", "B X", "C Z");
        Result result = process(input);
        assertThat(result.part1Score()).isEqualTo(15);
        assertThat(result.part2Score()).isEqualTo(12);
    }

}