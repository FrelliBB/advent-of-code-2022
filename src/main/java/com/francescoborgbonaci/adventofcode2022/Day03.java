package com.francescoborgbonaci.adventofcode2022;

import com.francescoborgbonaci.InputUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

public class Day03 {

    private static final Map<Character, Integer> PRIORITIES = new HashMap<>();

    static {
        populatePriorities();
    }

    public static void main(String[] args) throws Exception {
        final Result sum = InputUtils.processFileAsLines("day3.txt", Day03::process);
        System.out.println("Part 1: " + sum.duplicateItemPriority());
        System.out.println("Part 2: " + sum.groupBadgePriority());
    }

    public static Result process(final List<String> lines) {
        final AtomicInteger chunkCounter = new AtomicInteger();

        return lines.stream()
            .map(Rucksack::new)
            .collect(collectingAndThen(
                groupingBy(rucksack -> chunkCounter.getAndIncrement() / 3), // chunk elves into groups of 3
                groupedRucksacks -> groupedRucksacks.values().stream().map(ElfGroup::new)
            ))
            .map(elfGroup -> new Result(elfGroup.getPrioritySumOfSharedItems(), elfGroup.getPriorityOfGroupBadgeItem()))
            .reduce(new Result(0, 0), Result::add);
    }

    record Result(int duplicateItemPriority, int groupBadgePriority) {
        public Result add(Result other) {
            return new Result(duplicateItemPriority + other.duplicateItemPriority, groupBadgePriority + other.groupBadgePriority);
        }
    }

    record ElfGroup(List<Rucksack> rucksacks) {
        public int getPrioritySumOfSharedItems() {
            return rucksacks.stream().map(Rucksack::getSharedItem).mapToInt(PRIORITIES::get).sum();
        }

        public int getPriorityOfGroupBadgeItem() {
            final var sharedItem = getSharedItem(rucksacks.stream().map(Rucksack::contents).toList());
            return PRIORITIES.get(sharedItem);
        }
    }

    record Rucksack(String contents) {
        public char getSharedItem() {
            final var compartment1 = contents.substring(0, contents.length() / 2);
            final var compartment2 = contents.substring(contents.length() / 2);
            return Day03.getSharedItem(List.of(compartment1, compartment2));
        }
    }

    private static void populatePriorities() {
        final char start = 'a';
        IntStream.range(0, 26).forEach(i -> {
            PRIORITIES.put((char) (start + i), i + 1);
            PRIORITIES.put((char) (Character.toUpperCase(start) + i), i + 1 + 26);
        });
    }

    private static Set<Character> toCharacterSet(String string) {
        return string.chars().mapToObj(value -> (char) value).collect(Collectors.toSet());
    }

    private static Character getSharedItem(List<String> strings) {
        final String sharedString = strings.stream().reduce((s1, s2) -> {
            Set<Character> set = toCharacterSet(s1);
            set.retainAll(toCharacterSet(s2));
            char[] charArray = set.stream().map(Object::toString).collect(Collectors.joining())
                .toCharArray();
            return new String(charArray);
        }).orElseThrow();

        if (sharedString.length() != 1) {
            throw new IllegalArgumentException("No common characters among strings " + strings);
        }
        return sharedString.charAt(0);
    }

}
