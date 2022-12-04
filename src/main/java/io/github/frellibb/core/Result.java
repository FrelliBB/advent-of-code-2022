package io.github.frellibb.core;

public interface Result {

    Object part1();

    Object part2();

    default String print() {
        return """
            Part 1: %s
            Part 2: %s
            """.formatted(part1(), part2());
    }
}
