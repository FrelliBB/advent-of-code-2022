package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class Day06Test extends AbstractDayTest {

    public static Stream<Arguments> otherExamplesArguments() {
        return Stream.of(
            Arguments.arguments("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23),
            Arguments.arguments("nppdvjthqldpwncqszvftbrmjlhg", 6, 23),
            Arguments.arguments("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29),
            Arguments.arguments("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
        );
    }

    @ParameterizedTest
    @MethodSource("otherExamplesArguments")
    void otherExamples(String example, int expectedResultPart1, int expectedResultPart2) {
        final var result = getDay().process(List.of(example));
        Assertions.assertThat(result.part1()).isEqualTo(expectedResultPart1);
        Assertions.assertThat(result.part2()).isEqualTo(expectedResultPart2);
    }

    @Override
    Day getDay() {
        return new Day06();
    }

    @Override
    List<String> example() {
        return List.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb");
    }

    @Override
    Object expectedPart1Result() {
        return 7;
    }

    @Override
    Object expectedPart2Result() {
        return 19;
    }
}
