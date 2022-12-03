package com.francescoborgbonaci.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.francescoborgbonaci.adventofcode2022.Day03.Result;
import static com.francescoborgbonaci.adventofcode2022.Day03.Rucksack;
import static com.francescoborgbonaci.adventofcode2022.Day03.process;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void sampleData() {
        final List<String> input = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        );
        final Result result = process(input);

        assertThat(new Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp").getSharedItemInRucksack()).isEqualTo('p');
        assertThat(result.duplicateItemPriority()).isEqualTo(157);
        assertThat(result.groupBadgePriority()).isEqualTo(70);
    }
}