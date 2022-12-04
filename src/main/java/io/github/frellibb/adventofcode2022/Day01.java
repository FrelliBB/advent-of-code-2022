package io.github.frellibb.adventofcode2022;

import io.github.frellibb.ListUtils;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.List;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Day01 implements Day {

    public ElvesData process(List<String> lines) {
        return ListUtils.splitListByPredicate(lines, String::isBlank)
            .stream().map(strings -> ListUtils.mapListEntries(strings, Integer::parseInt))
            .map(Elf::new)
            .collect(collectingAndThen(toList(), ElvesData::new));
    }

    record ElvesData(List<Elf> elves) implements Result {

        public Integer getTotalCaloriesHeldByTopNElves(int n) {
            return elves.stream()
                .map(Elf::getTotalCalories)
                .sorted(reverseOrder())
                .limit(n)
                .reduce(Integer::sum)
                .orElse(0);
        }

        @Override
        public Object part1() {
            return getTotalCaloriesHeldByTopNElves(1);
        }

        @Override
        public Object part2() {
            return getTotalCaloriesHeldByTopNElves(3);
        }
    }

    record Elf(List<Integer> calories) {
        public Integer getTotalCalories() {
            return calories.stream().reduce(Integer::sum).orElse(0);
        }
    }

}
