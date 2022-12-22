package io.github.frellibb.adventofcode2022;

import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day07 implements Day {

    private static final long MAX_SIZE = 100_000L;
    private static final long TOTAL_DISK_SPACE = 70_000_000;
    private static final long REQUIRED_SPACE = 30_000_000;

    @Override
    public Result process(final List<String> lines) {
        Directory root = parseRoot(lines);
        final var allDirectories = findAllDirectories(root);
        final var part1 = allDirectories.stream().map(Directory::calculateSize)
            .filter(aLong -> aLong <= MAX_SIZE)
            .reduce(0L, Long::sum);

        var rootSize = root.calculateSize();
        var availableSpace = TOTAL_DISK_SPACE - rootSize;
        var spaceWeNeedToFree = REQUIRED_SPACE - availableSpace;

        final var min = allDirectories.stream()
            .map(Directory::calculateSize)
            .filter(size -> size >= spaceWeNeedToFree)
            .min(Long::compareTo)
            .orElseThrow();

        return new Result() {
            @Override
            public Object part1() {
                return part1;
            }

            @Override
            public Object part2() {
                return min;
            }
        };
    }

    private List<Directory> findAllDirectories(Directory root) {
        final var dirs = new ArrayList<Directory>(List.of(root));
        root.children().forEach(node -> {
            if (node instanceof Directory directory) {
                dirs.addAll(findAllDirectories(directory));
            }
        });
        return dirs;
    }

    private Directory parseRoot(List<String> lines) {
        Directory root = new Directory("/", new ArrayList<>(), null);
        Directory current = root;

        for (String line : lines) {
            if (line.startsWith("$ cd")) {
                if (line.equals("$ cd /")) {
                } else if (line.equals("$ cd ..")) { // Move up
                    current = current.parent;
                } else {
                    final var name = line.substring(5);
                    current = (Directory) current.children.stream()
                        .filter(c -> c.name().equals(name))
                        .findFirst()
                        .orElseThrow();
                }
            } else if (line.startsWith("$ ls")) {

            } else if (line.startsWith("dir ")) {
                current.children.add(new Directory(line.split(" ")[1], new ArrayList<>(), current));
            } else {
                var split = line.split(" ");
                current.children.add(new File(split[1], Long.parseLong(split[0])));
            }
        }
        return root;
    }

    record Directory(String name, List<Node> children, Directory parent) implements Node {
        public long calculateSize() {
            return children.stream().map(child -> switch (child) {
                case File f -> f.calculateSize();
                case Directory dir -> dir.calculateSize();
            }).reduce(0L, Long::sum);
        }

        @Override
        public String toString() {
            return "dir %s %s".formatted(name, children.stream().map(Node::name).collect(Collectors.toSet()));
        }
    }

    record File(String name, long size) implements Node {
        public long calculateSize() {
            return size;
        }

        @Override
        public String toString() {
            return "%s %d".formatted(name, size);
        }
    }

    sealed interface Node permits File, Directory {
        long calculateSize();

        String name();
    }
}
