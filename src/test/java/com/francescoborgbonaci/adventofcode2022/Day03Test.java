package com.francescoborgbonaci.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void sampleData() {
        final var input = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        );
        final var result = Day03.process(input);

        assertThat(new Day03.Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp").getSharedItem()).isEqualTo('p');
        assertThat(result.duplicateItemPriority()).isEqualTo(157);
        assertThat(result.groupBadgePriority()).isEqualTo(70);
    }
}