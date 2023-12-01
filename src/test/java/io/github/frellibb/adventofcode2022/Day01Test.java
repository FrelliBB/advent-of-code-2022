package io.github.frellibb.adventofcode2022;

import io.github.frellibb.adventofcode.y2022.Day01;
import io.github.frellibb.adventofcode.core.Day;

import java.util.List;

class Day01Test extends AbstractDayTest {

    @Override
    Day getDay() {
        return new Day01();
    }

    @Override
    List<String> example() {
        return List.of(
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
    }

    @Override
    Object expectedPart1Result() {
        return 24_000;
    }

    @Override
    Object expectedPart2Result() {
        return 45_000;
    }
}
