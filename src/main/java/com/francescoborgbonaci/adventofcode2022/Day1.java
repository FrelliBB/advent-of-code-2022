package com.francescoborgbonaci.adventofcode2022;

import com.francescoborgbonaci.ListUtils;

import java.util.List;

import static com.francescoborgbonaci.InputUtils.processFileAsLines;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Day1 {

    public static void main(String[] args) throws Exception {
        final ElvesData elvesData = processFileAsLines("day1.txt", Day1::parse);

        System.out.println("Part 1: " + elvesData.getTotalCaloriesHeldByElfWithMostCalories());
        System.out.println("Part 2: " + elvesData.getTotalCaloriesHeldByTopNElves(3));
    }

    public static ElvesData parse(final List<String> lines) {
        return ListUtils.splitListByPredicate(lines, String::isBlank)
            .stream().map(strings -> ListUtils.mapListEntries(strings, Integer::parseInt))
            .map(Elf::new)
            .collect(collectingAndThen(toList(), ElvesData::new));
    }

    record ElvesData(List<Elf> elves) {

        public Integer getTotalCaloriesHeldByElfWithMostCalories() {
            return getTotalCaloriesHeldByTopNElves(1);
        }

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
