package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;

class Day03Test extends AbstractDayTest {

    @Override
    Day getDay() {
        return new Day03();
    }

    @Override
    List<String> example() {
        return List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        );
    }

    @Override
    Object expectedPart1Result() {
        return 157;
    }

    @Override
    Object expectedPart2Result() {
        return 70;
    }
}