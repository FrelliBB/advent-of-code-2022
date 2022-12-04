package io.github.frellibb.adventofcode2022;

import io.github.frellibb.ListUtils;
import io.github.frellibb.core.Day;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class Day03 implements Day {

    private static final Map<Character, Integer> PRIORITIES = new HashMap<>();

    static {
        char start = 'a';
        IntStream.range(0, 26).forEach(i -> {
            PRIORITIES.put((char) (start + i), i + 1);
            PRIORITIES.put((char) (Character.toUpperCase(start) + i), i + 1 + 26);
        });
    }

    private static Set<Character> toCharacterSet(String string) {
        return string.chars().mapToObj(value -> (char) value).collect(toSet());
    }

    private static Character getCommonCharacter(List<String> strings) {
        String sharedString = strings.stream().reduce((s1, s2) -> {
            Set<Character> set = toCharacterSet(s1);
            set.retainAll(toCharacterSet(s2));
            char[] charArray = set.stream().map(Object::toString).collect(joining())
                .toCharArray();
            return new String(charArray);
        }).orElseThrow();

        if (sharedString.length() != 1) {
            throw new IllegalArgumentException("No common characters among strings " + strings);
        }
        return sharedString.charAt(0);
    }

    public Result process(List<String> lines) {
        List<Rucksack> rucksacks = lines.stream().map(Rucksack::new).toList();

        return ListUtils.chunk(rucksacks, 3)
            .stream().map(ElfGroup::new)
            .map(ElfGroup::toResult)
            .reduce(new Result(0, 0), Result::add);
    }

    record Result(int duplicateItemPriority, int groupBadgePriority) implements io.github.frellibb.core.Result {
        public Result add(Result other) {
            return new Result(
                duplicateItemPriority + other.duplicateItemPriority,
                groupBadgePriority + other.groupBadgePriority
            );
        }

        @Override
        public Object part1() {
            return duplicateItemPriority;
        }

        @Override
        public Object part2() {
            return groupBadgePriority;
        }
    }

    record ElfGroup(Collection<Rucksack> rucksacks) {
        public int getPrioritySumOfSharedItems() {
            return rucksacks.stream().map(Rucksack::getSharedItemInRucksack).mapToInt(PRIORITIES::get).sum();
        }

        public int getPriorityOfGroupBadgeItem() {
            Character sharedItem = getCommonCharacter(rucksacks.stream().map(Rucksack::contents).toList());
            return PRIORITIES.get(sharedItem);
        }

        public Result toResult() {
            return new Result(getPrioritySumOfSharedItems(), getPriorityOfGroupBadgeItem());
        }
    }

    record Rucksack(String contents) {
        public char getSharedItemInRucksack() {
            String compartment1 = contents.substring(0, contents.length() / 2);
            String compartment2 = contents.substring(contents.length() / 2);
            return getCommonCharacter(List.of(compartment1, compartment2));
        }
    }

}
