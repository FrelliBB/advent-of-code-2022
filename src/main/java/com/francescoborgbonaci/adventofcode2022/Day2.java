package com.francescoborgbonaci.adventofcode2022;

import com.francescoborgbonaci.InputUtils;

import java.util.List;

import static com.francescoborgbonaci.adventofcode2022.Day2.Hand.PAPER;
import static com.francescoborgbonaci.adventofcode2022.Day2.Hand.ROCK;
import static com.francescoborgbonaci.adventofcode2022.Day2.Hand.SCISSORS;
import static com.francescoborgbonaci.adventofcode2022.Day2.HandResult.DRAW;
import static com.francescoborgbonaci.adventofcode2022.Day2.HandResult.LOSE;
import static com.francescoborgbonaci.adventofcode2022.Day2.HandResult.WIN;

public class Day2 {

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

    enum Hand {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private final int points;

        Hand(final int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }

        public static Hand parse(String value) {
            return switch (value) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> throw new IllegalArgumentException(value);
            };
        }
    }

    enum HandResult {
        WIN(6),
        DRAW(3),
        LOSE(0);

        final int points;

        HandResult(final int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }

        public static HandResult parse(String value) {
            return switch (value) {
                case "X" -> LOSE;
                case "Y" -> DRAW;
                case "Z" -> WIN;
                default -> throw new IllegalArgumentException(value);
            };
        }
    }

    record Scoring(Hand playerHand, Hand opponentHand, HandResult result) {
        public int calculateScore() {
            return playerHand.getPoints() + result.getPoints();
        }
    }

    public static void main(String[] args) throws Exception {
        final var result = InputUtils.processFileAsLines("day2.txt", strings -> process(strings));
        System.out.println("Part 1: " + result.part1Score());
        System.out.println("Part 2: " + result.part2Score());
    }

    public static Result process(final List<String> lines) {
        final var part1Score = lines.stream()
            .map(s -> s.split(" "))
            .map(hands -> {
                final var opponentHand = Hand.parse(hands[0]);
                final var yourHand = Hand.parse(hands[1]);
                return getScoring(yourHand, opponentHand);
            }).mapToInt(Scoring::calculateScore)
            .sum();

        final var part2Score = lines.stream()
            .map(s -> s.split(" "))
            .map(hands -> {
                final var opponentHand = Hand.parse(hands[0]);
                final var result = HandResult.parse(hands[1]);
                return getScoring(opponentHand, result);
            }).mapToInt(Scoring::calculateScore)
            .sum();
        return new Result(part1Score, part2Score);
    }

    private static Scoring getScoring(final Hand opponentHand, final HandResult result) {
        return SCORING.stream()
            .filter(scoring -> scoring.result() == result)
            .filter(scoring -> scoring.opponentHand() == opponentHand)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("No scoring found to [%s] vs. [%s]".formatted(result, opponentHand)));
    }

    public static Scoring getScoring(Hand playerHand, Hand opponentHand) {
        return SCORING.stream()
            .filter(scoring -> scoring.playerHand() == playerHand)
            .filter(scoring -> scoring.opponentHand() == opponentHand)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("No scoring found for [%s] vs. [%s]".formatted(playerHand, opponentHand)));
    }

    record Result(int part1Score, int part2Score) {
    }

}
