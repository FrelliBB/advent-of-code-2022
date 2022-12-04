package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.frellibb.adventofcode2022.Day02.Hand.PAPER;
import static io.github.frellibb.adventofcode2022.Day02.Hand.ROCK;
import static io.github.frellibb.adventofcode2022.Day02.Hand.SCISSORS;
import static io.github.frellibb.adventofcode2022.Day02.Outcome.DRAW;
import static io.github.frellibb.adventofcode2022.Day02.Outcome.LOSE;
import static io.github.frellibb.adventofcode2022.Day02.Outcome.WIN;

public class Day02 implements Day {

    public Result process(List<String> lines) {
        return lines.stream()
            .map(Day02::getResultForStrategy)
            .reduce(new Result(0, 0), Result::add);
    }

    record Scoring(Hand playerHand, Hand opponentHand, Outcome result) {
        public int calculateScore() {
            return playerHand.getPoints() + result.getPoints();
        }
    }

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

    private static Optional<Scoring> getScoring(Predicate<Scoring> predicate) {
        return SCORING.stream().filter(predicate).findAny();
    }

    private static Result getResultForStrategy(String strategy) {
        String[] strategyPart = strategy.split(" ");

        Hand opponentHand = Hand.from(strategyPart[0]);

        // part 1
        Hand yourHand = Hand.from(strategyPart[1]);
        Scoring part1Score = getScoring(s -> s.opponentHand() == opponentHand && s.playerHand() == yourHand).orElseThrow();

        // part 2
        Outcome outcome = Outcome.from(strategyPart[1]);
        Scoring part2Score = getScoring(s -> s.opponentHand() == opponentHand && s.result() == outcome).orElseThrow();

        return new Result(part1Score.calculateScore(), part2Score.calculateScore());
    }

    enum Hand {
        ROCK,
        PAPER,
        SCISSORS;

        public int getPoints() {
            return ordinal() + 1;
        }

        public static Hand from(String value) {
            return switch (value) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> throw new IllegalArgumentException(value);
            };
        }
    }

    enum Outcome {
        LOSE,
        DRAW,
        WIN;

        public int getPoints() {
            return ordinal() * 3;
        }

        public static Outcome from(String value) {
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

}
