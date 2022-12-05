package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;

public class Day05Test extends AbstractDayTest {
    @Override
    Day getDay() {
        return new Day05();
    }

    @Override
    List<String> example() {
        return """
                [D]
            [N] [C]
            [Z] [M] [P]
             1   2   3
                        
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
            """.lines().toList();
    }

    @Override
    Object expectedPart1Result() {
        return "CMZ";
    }

    @Override
    Object expectedPart2Result() {
        return "MCD";
    }
}
