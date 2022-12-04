package io.github.frellibb.adventofcode2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.frellibb.adventofcode2022.Day03.Result;
import static io.github.frellibb.adventofcode2022.Day03.Rucksack;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

    @Test
    void sampleData() {
        List<String> input = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        );
        Result result = new Day03().process(input);

        assertThat(new Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp").getSharedItemInRucksack()).isEqualTo('p');
        assertThat(result.duplicateItemPriority()).isEqualTo(157);
        assertThat(result.groupBadgePriority()).isEqualTo(70);
    }
}