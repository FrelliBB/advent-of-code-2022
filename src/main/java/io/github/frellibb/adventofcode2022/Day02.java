package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.frellibb.adventofcode2022.Day02.Hand.PAPER;
import static io.github.frellibb.adventofcode2022.Day02.Hand.ROCK;
import static io.github.frellibb.adventofcode2022.Day02.Hand.SCISSORS;
import static io.github.frellibb.adventofcode2022.Day02.HandResult.DRAW;
import static io.github.frellibb.adventofcode2022.Day02.HandResult.LOSE;
import static io.github.frellibb.adventofcode2022.Day02.HandResult.WIN;

public class Day02 implements Day {

    private static final List<Scoring> SCORING = List.of(
        new Scoring(ROCK, ROCK, DRAW),
        new Scoring(ROCK, PAPER, LOSE),
        new Scoring(ROCK, SCISSORS, WIN),
        new Scoring(PAPER, ROCK, WIN),
        new Scoring(PAPER, PAPER, DRAW),
        new Scoring(PAPER, SCISSORS, LOSE),
        new Scoring(SCISSORS, ROCK, LOSE),
        new Scoring(SCISSORS, PAPER, WIN),
        new Scoring(SCISSORS, SCISSORS, DRAW)
    );

    private static Result getResultForStrategy(String strategy) {
        String[] strategyPart = strategy.split(" ");

        Hand opponentHand = Hand.parse(strategyPart[0]);
        Hand yourHand = Hand.parse(strategyPart[1]);
        Scoring part1Score = getScoring(s -> s.opponentHand() == opponentHand && s.playerHand() == yourHand).orElseThrow();

        HandResult result = HandResult.parse(strategyPart[1]);
        Scoring part2Score = getScoring(s -> s.opponentHand() == opponentHand && s.result() == result).orElseThrow();

        return new Result(part1Score.calculateScore(), part2Score.calculateScore());
    }

    private static Optional<Scoring> getScoring(Predicate<Scoring> predicate) {
        return SCORING.stream().filter(predicate).findAny();
    }

    public Result process(List<String> lines) {
        return lines.stream()
            .map(Day02::getResultForStrategy)
            .reduce(new Result(0, 0), Result::add);
    }

    @RequiredArgsConstructor
    enum Hand {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        @Getter
        private final int points;

        public static Hand parse(String value) {
            return switch (value) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> throw new IllegalArgumentException(value);
            };
        }
    }

    @RequiredArgsConstructor
    enum HandResult {
        WIN(6),
        DRAW(3),
        LOSE(0);

        @Getter
        final int points;

        public static HandResult parse(String value) {
            return switch (value) {
                case "X" -> LOSE;
                case "Y" -> DRAW;
                case "Z" -> WIN;
                default -> throw new IllegalArgumentException(value);
            };
        }
    }

    record Result(int part1Score, int part2Score) implements io.github.frellibb.core.Result {
        public Result add(Result other) {
            return new Result(part1Score + other.part1Score, part2Score + other.part2Score);
        }

        @Override
        public Object part1() {
            return part1Score;
        }

        @Override
        public Object part2() {
            return part2Score;
        }
    }

    record Scoring(Hand playerHand, Hand opponentHand, HandResult result) {
        public int calculateScore() {
            return playerHand.getPoints() + result.getPoints();
        }
    }

}
