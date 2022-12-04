package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;

import static io.github.frellibb.ListUtils.mapListEntries;
import static io.github.frellibb.ListUtils.splitListByPredicate;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Day01 implements Day {

    public Result process(List<String> lines) {
        return splitListByPredicate(lines, String::isBlank)
            .stream()
            .map(list -> mapListEntries(list, Integer::parseInt))
            .map(Elf::new)
            .collect(collectingAndThen(toList(), Result::new));
    }

    record Result(List<Elf> elves) implements io.github.frellibb.core.Result {

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
