package io.github.frellibb.adventofcode2022;

import io.github.frellibb.ListUtils;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isAlphabetic;
import static java.util.stream.IntStream.rangeClosed;

public class Day05 implements Day {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    @Override
    public Result process(final List<String> lines) {
        final List<List<String>> cratesAndInstructions = ListUtils.splitListByPredicate(lines, String::isBlank);
        final List<String> crateLines = cratesAndInstructions.get(0);
        final List<String> instructionLines = cratesAndInstructions.get(1);

        final Map<Integer, Stack<Character>> crateStacks = new HashMap<>();
        for (int crate = crateLines.size()-1; crate >= 0; crate--) {
            final String crateLine = crateLines.get(crate);
            final List<Character> crateElements = getCrateElements(crateLine);
            rangeClosed(1, crateElements.size()).forEach(i -> {
                if (!crateStacks.containsKey(i)) {
                    crateStacks.put(i, new Stack<>());
                }
                final Character codePoint = crateElements.get(i - 1);
                if (isAlphabetic(codePoint)) {
                    crateStacks.compute(i, (integer, characters) -> {
                        characters.push(codePoint);
                        return characters;
                    });
                }
            });
        }

        instructionLines.stream().map(Instruction::from).forEach(instruction -> {
            for (int i = 0; i < instruction.amount(); i++) {
                crateStacks.get(instruction.to()).push(crateStacks.get(instruction.from()).pop());
            }
        });

        final var part1 = crateStacks.values().stream().map(characters -> characters.peek()).map(character -> String.valueOf(character))
            .collect(Collectors.joining());

        // 19 -> 1, 5, 9, 13, 17
        // 15 -> 1, 5, 9, 13
        // 11 -> 1, 5, 9
        // 7 -> 1, 5
        // 3 -> 1

        return new Result() {
            @Override
            public Object part1() {
                return part1;
            }

            @Override
            public Object part2() {
                return null;
            }
        };
    }

    record Instruction(int amount, int from, int to) {
        public static Instruction from(String instructionLine) {
            final var matchResult = INSTRUCTION_PATTERN.matcher(instructionLine);
            if (matchResult.matches()) {
                final var amount = matchResult.group(1);
                final var from = matchResult.group(2);
                final var to = matchResult.group(3);
                return new Instruction(Integer.parseInt(amount), Integer.parseInt(from), Integer.parseInt(to));
            }
            throw new IllegalArgumentException(instructionLine);
        }
    }

    public List<Character> getCrateElements(String str) {
        List<Character> elements = new ArrayList<>();

        for (int i = 1; i < str.length(); i += 4) {
            elements.add(str.charAt(i));
        }

        return elements;
    }

}
