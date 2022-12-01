package com.francescoborgbonaci.adventofcode2022;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.francescoborgbonaci.InputUtils.processFileAsLines;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Day1 {

    public static void main(String[] args) throws Exception {
        final var elvesData = processFileAsLines("day1.txt", Day1::parse);

        System.out.println("Part 1: " + elvesData.getTotalCaloriesHeldByElfWithMostCalories());
        System.out.println("Part 2: " + elvesData.getTotalCaloriesHeldByTopNElves(3));
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

    public static ElvesData parse(final List<String> lines) {
        int[] indexes = getIndicesToSublistOn(lines);

        return IntStream.range(0, indexes.length - 1)
            .mapToObj(i -> sublist(lines, indexes, i))
            .map(Day1::stringsToIntegers)
            .map(Elf::new)
            .collect(collectingAndThen(toList(), ElvesData::new));
    }

    private static int[] getIndicesToSublistOn(final List<String> lines) {
        return Stream.of(
            IntStream.of(-1), // start of list
            IntStream.range(0, lines.size()).filter(i -> lines.get(i).isEmpty()), // indices of blank lines
            IntStream.of(lines.size()) // end of list
        ).flatMapToInt(s -> s).toArray();
    }

    private static List<String> sublist(final List<String> lines, final int[] indexes, final int i) {
        final var from = indexes[i] + 1;
        final var to = indexes[i + 1];
        return lines.subList(from, to);
    }

    private static List<Integer> stringsToIntegers(final List<String> strings) {
        return strings.stream().map(Integer::parseInt).toList();
    }
}
