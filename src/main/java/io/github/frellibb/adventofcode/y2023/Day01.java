package io.github.frellibb.adventofcode.y2023;

import io.github.frellibb.adventofcode.core.BasicResult;
import io.github.frellibb.adventofcode.core.Day;
import io.github.frellibb.adventofcode.core.Result;

import java.util.List;

public class Day01 implements Day {
    @Override
    public Result process(final List<String> lines) {
        final Integer result = lines.stream()
            .map(Day01::processLine)
            .map(Integer::parseInt)
            .reduce(0, Integer::sum);

        return new BasicResult(result, null);
    }

    private static String processLine(final String line) {
        final String digitsOnly = line.replaceAll("[^0-9]", "");
        final char firstDigit = digitsOnly.charAt(0);
        final char lastDigit = digitsOnly.charAt(digitsOnly.length() - 1);
        return "%s%s".formatted(firstDigit, lastDigit);
    }
}
