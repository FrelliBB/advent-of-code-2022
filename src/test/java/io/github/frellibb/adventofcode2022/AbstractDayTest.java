package io.github.frellibb.adventofcode2022;

import io.github.frellibb.adventofcode.core.Day;
import io.github.frellibb.adventofcode.core.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDayTest {

    @Test
    void examplePart1ShouldMatch() {
        assertThat(getResult().part1()).isNotNull();
        assertThat(getResult().part1()).isEqualTo(expectedPart1Result());
    }

    @Test
    void examplePart2ShouldMatch() {
        assertThat(getResult().part2()).isNotNull();
        assertThat(getResult().part2()).isEqualTo(expectedPart2Result());
    }

    private Result getResult() {
        return getDay().process(example());
    }

    abstract Day getDay();

    abstract List<String> example();

    abstract Object expectedPart1Result();

    abstract Object expectedPart2Result();

}
