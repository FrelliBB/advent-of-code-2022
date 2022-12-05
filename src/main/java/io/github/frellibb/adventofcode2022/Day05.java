package io.github.frellibb.adventofcode2022;

import io.github.frellibb.ListUtils;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isAlphabetic;
import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Day05 implements Day {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    @Override
    public Result process(final List<String> lines) {
        final List<List<String>> cratesAndInstructions = ListUtils.splitListByPredicate(lines, String::isBlank);
        final var crateLines = cratesAndInstructions.get(0);
        final List<Instruction> instructions = cratesAndInstructions.get(1).stream().map(Instruction::from).toList();

        return new Result() {
            @Override
            public Object part1() {
                return crateMover9000(parseCrateStacks(crateLines), instructions);
            }

            @Override
            public Object part2() {
                return crateMover9001(parseCrateStacks(crateLines), instructions);
            }
        };
    }

    private String crateMover9001(final Map<Integer, Stack<Character>> crates, final List<Instruction> instructions) {
        instructions.forEach(instruction -> {
            final var crateMoverStack = new Stack<Character>();
            range(0, instruction.amount()).mapToObj(i -> crates.get(instruction.from()).pop()).forEach(crateMoverStack::push);
            range(0, crateMoverStack.size()).forEach(i -> crates.get(instruction.to).push(crateMoverStack.pop()));
        });

        return getTopOfEachStack(crates);
    }

    private String crateMover9000(Map<Integer, Stack<Character>> crates, List<Instruction> instructions) {
        instructions.forEach(instruction -> {
            range(0, instruction.amount()).forEach(_unused -> {
                final var crate = crates.get(instruction.from()).pop();
                crates.get(instruction.to()).push(crate);
            });
        });

        return getTopOfEachStack(crates);
    }

    private String getTopOfEachStack(final Map<Integer, Stack<Character>> crates) {
        return crates.values().stream()
            .map(Stack::peek)
            .map(String::valueOf)
            .collect(Collectors.joining());
    }

    private Map<Integer, Stack<Character>> parseCrateStacks(final List<String> crateLines) {
        final Map<Integer, Stack<Character>> crateStacks = new HashMap<>();
        iterate(crateLines.size() - 1, crate -> crate >= 0, crate -> crate - 1)
            .mapToObj(crateLines::get)
            .map(this::getCrateElements)
            .forEach(crateElements -> rangeClosed(1, crateElements.size()).forEach(i -> {
                if (!crateStacks.containsKey(i)) {
                    crateStacks.put(i, new Stack<>());
                }
                final Character codePoint = crateElements.get(i - 1);
                if (isAlphabetic(codePoint)) {
                    crateStacks.computeIfPresent(i, (integer, characters) -> {
                        characters.push(codePoint);
                        return characters;
                    });
                }
            }));
        return crateStacks;
    }

    record Instruction(int amount, int from, int to) {
        public static Instruction from(String instructionLine) {
            final Matcher matchResult = INSTRUCTION_PATTERN.matcher(instructionLine);
            if (matchResult.matches()) {
                final String amount = matchResult.group(1);
                final String from = matchResult.group(2);
                final String to = matchResult.group(3);
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
