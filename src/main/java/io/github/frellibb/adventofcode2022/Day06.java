package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.BasicResult;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayDeque;
import java.util.List;

public class Day06 implements Day {
    @Override
    public Result process(final List<String> lines) {
        final var input = lines.get(0);

        int part1 = getMarkerIndex(input, 4);
        int part2 = getMarkerIndex(input, 14);

        return new BasicResult(part1, part2);
    }

    private int getMarkerIndex(final String input, int markerLength) {
        final var marker = new ArrayDeque<Character>(markerLength);

        for (int i = 0; i < input.toCharArray().length; i++) {
            final var currentChar = input.charAt(i);
            marker.addLast(currentChar);
            if (marker.size() < markerLength) {
                continue;
            }

            if (marker.stream().distinct().count() == markerLength) {
                return i + 1;
            }

            marker.removeFirst();
        }

        throw new IllegalStateException(input);
    }

}
