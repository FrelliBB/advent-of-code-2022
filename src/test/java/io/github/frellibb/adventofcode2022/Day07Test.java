package io.github.frellibb.adventofcode2022;

import io.github.frellibb.adventofcode.y2022.Day07;
import io.github.frellibb.adventofcode.core.Day;

import java.util.List;

class Day07Test extends AbstractDayTest {

    @Override
    Day getDay() {
        return new Day07();
    }

    @Override
    List<String> example() {
        return List.of("$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "$ cd a",
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd ..",
            "$ cd d",
            "$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k"
        );
    }

    @Override
    Object expectedPart1Result() {
        return 95437L;
    }

    @Override
    Object expectedPart2Result() {
        return 24933642L;
    }
}