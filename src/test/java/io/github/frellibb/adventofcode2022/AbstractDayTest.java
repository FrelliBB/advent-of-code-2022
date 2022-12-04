package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDayTest {

    @Test
    void examplePart1ShouldMatch() {
        assertThat(getDay().process(example()).part1()).isEqualTo(expectedPart1Result());
    }

    @Test
    void examplePart2ShouldMatch() {
        assertThat(getDay().process(example()).part2()).isEqualTo(expectedPart2Result());
    }

    abstract Day getDay();

    abstract List<String> example();

    abstract Object expectedPart1Result();

    abstract Object expectedPart2Result();

}
