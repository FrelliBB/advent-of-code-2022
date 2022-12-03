package io.github.frellibb.adventofcode2022;

import io.github.frellibb.ListUtils;

import java.util.List;

import static io.github.frellibb.InputUtils.processFileAsLines;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Day01 {

    public static void main(String[] args) throws Exception {
        final ElvesData elvesData = processFileAsLines("day1.txt", Day01::process);

        System.out.println("Part 1: " + elvesData.getTotalCaloriesHeldByTopNElves(1));
        System.out.println("Part 2: " + elvesData.getTotalCaloriesHeldByTopNElves(3));
    }

    public static ElvesData process(final List<String> lines) {
        return ListUtils.splitListByPredicate(lines, String::isBlank)
            .stream().map(strings -> ListUtils.mapListEntries(strings, Integer::parseInt))
            .map(Elf::new)
            .collect(collectingAndThen(toList(), ElvesData::new));
    }

    record ElvesData(List<Elf> elves) {

        public Integer getTotalCaloriesHeldByTopNElves(int n) {
            return elves.stream()
                .map(Elf::getTotalCalories)
                .sorted(reverseOrder())
                .limit(n)
                .reduce(Integer::sum)
                .orElse(0);
        }
    }

    record Elf(List<Integer> calories) {
        public Integer getTotalCalories() {
            return calories.stream().reduce(Integer::sum).orElse(0);
        }
    }

}
