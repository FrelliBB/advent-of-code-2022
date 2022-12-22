package io.github.frellibb.adventofcode2022;

import io.github.frellibb.adventofcode2022.Day07.Commands.Command;
import io.github.frellibb.adventofcode2022.Day07.Commands.GoToDirectory;
import io.github.frellibb.adventofcode2022.Day07.Commands.GoToRoot;
import io.github.frellibb.adventofcode2022.Day07.Commands.ListedDirectory;
import io.github.frellibb.adventofcode2022.Day07.Commands.ListedFile;
import io.github.frellibb.adventofcode2022.Day07.FileSystem.Directory;
import io.github.frellibb.adventofcode2022.Day07.FileSystem.File;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayList;
import java.util.List;
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

    private Directory parseRoot(List<String> lines) {

        Directory root = new Directory("/", new ArrayList<>(), null);
        final List<Commands.Command> commands = lines.stream().map(s -> switch (s) {
            case "$ cd /" -> new GoToRoot();
            case "$ cd .." -> new Commands.GoBack();
            case "$ ls" -> new Commands.ListNodes();
            case String st && s.startsWith("$ cd ") -> new GoToDirectory(st.split(" ")[2]);
            case String st && st.startsWith("dir") -> new ListedDirectory(st.split(" ")[1]);
            case String st -> new ListedFile(st.split(" ")[1], Long.parseLong(st.split(" ")[0]));
        }).map(record -> (Command) record).toList();

        for (final Command command : commands) {
            root = command.update(root);
        }

        return new GoToRoot().update(root);
    }

    private List<Directory> findAllDirectories(Directory root) {
        final var dirs = new ArrayList<>(List.of(root));
        root.children().forEach(node -> {
            if (node instanceof Directory directory) {
                dirs.addAll(findAllDirectories(directory));
            }
        });
        return dirs;
    }

    static class Commands {
        sealed interface Command permits GoBack, GoToDirectory, GoToRoot, ListedDirectory, ListedFile, ListNodes {
            Directory update(Directory pwd);
        }

        record ListNodes() implements Command {
            @Override
            public Directory update(final Directory pwd) {
                return pwd;
            }
        }

        record ListedFile(String name, long size) implements Command {

            @Override
            public Directory update(final Directory pwd) {
                pwd.children().add(new File(name, size));
                return pwd;
            }
        }

        record ListedDirectory(String name) implements Command {

            @Override
            public Directory update(final Directory pwd) {
                final var directory = new Directory(name, new ArrayList<>(), pwd);
                pwd.children().add(directory);
                return pwd;
            }
        }

        record GoBack() implements Command {

            @Override
            public Directory update(final Directory pwd) {
                return pwd.parent();
            }
        }

        record GoToDirectory(String dir) implements Command {

            @Override
            public Directory update(final Directory pwd) {
                return (Directory) pwd.children().stream().filter(node -> node.name().equals(dir)).findFirst().orElseThrow();
            }
        }

        record GoToRoot() implements Command {

            @Override
            public Directory update(final Directory pwd) {
                Directory root = pwd;
                while (root.parent() != null) {
                    root = root.parent();
                }
                return root;
            }
        }
    }

    static class FileSystem {
        record Directory(String name, List<Node> children, Directory parent) implements Node {
            private long calculateSize() {
                return children.stream().map(child -> switch (child) {
                    case File f -> f.size();
                    case Directory dir -> dir.calculateSize();
                }).reduce(0L, Long::sum);
            }

            public String toString() {
                return "dir %s %s".formatted(name, children.stream().map(Node::name).collect(Collectors.toSet()));
            }
        }

        record File(String name, long size) implements Node {
            public String toString() {
                return "%s %d".formatted(name, size);
            }
        }

        sealed interface Node permits File, Directory {

            String name();
        }
    }

}
