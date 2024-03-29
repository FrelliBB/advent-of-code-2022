package io.github.frellibb.adventofcode.y2022;

import io.github.frellibb.adventofcode.core.BasicResult;
import io.github.frellibb.adventofcode.core.Day;
import io.github.frellibb.adventofcode.core.Result;
import org.apache.commons.lang3.Range;

import java.util.List;

import static java.lang.Integer.parseInt;

public class Day04 implements Day {

    @Override
    public Result process(final List<String> lines) {
        final var numberOfFullyContainedRanges = lines.stream().map(Ranges::from)
            .filter(Ranges::fullyContainsAnother)
            .count();

        final var numberOfOverlappingRanges = lines.stream().map(Ranges::from)
            .filter(Ranges::overlap)
            .count();

        return new BasicResult(numberOfFullyContainedRanges, numberOfOverlappingRanges);
    }

    record Ranges(Range<Integer> range1, Range<Integer> range2) {
        public static Ranges from(String line) {
            final var ranges = line.split(",");
            final var range1 = ranges[0].split("-");
            final var range2 = ranges[1].split("-");
            final var r1 = Range.between(parseInt(range1[0]), parseInt(range1[1]));
            final var r2 = Range.between(parseInt(range2[0]), parseInt(range2[1]));
            return new Ranges(r1, r2);
        }

        public boolean fullyContainsAnother() {
            return range1.containsRange(range2) || range2.containsRange(range1);
        }

        public boolean overlap() {
            return range1.isOverlappedBy(range2);
        }
    }

}
