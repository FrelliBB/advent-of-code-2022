package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;

class Day04Test extends AbstractDayTest {

    @Override
    Day getDay() {
        return new Day04();
    }

    @Override
    List<String> example() {
        return List.of(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
        );
    }

    @Override
    Object expectedPart1Result() {
        return 2;
    }

    @Override
    Object expectedPart2Result() {
        return 4;
    }
}