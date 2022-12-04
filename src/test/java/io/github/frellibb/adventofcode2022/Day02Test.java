package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;

class Day02Test extends AbstractDayTest {

    @Override
    Day getDay() {
        return new Day02();
    }

    @Override
    List<String> example() {
        return List.of("A Y", "B X", "C Z");
    }

    @Override
    Object expectedPart1Result() {
        return 15;
    }

    @Override
    Object expectedPart2Result() {
        return 12;
    }
}