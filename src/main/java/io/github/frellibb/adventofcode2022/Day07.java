package io.github.frellibb.adventofcode2022;

import io.github.frellibb.adventofcode2022.Day07.Commands.Command;
import io.github.frellibb.adventofcode2022.Day07.Commands.GoToDirectory;
import io.github.frellibb.adventofcode2022.Day07.Commands.GoToRoot;
import io.github.frellibb.adventofcode2022.Day07.Commands.ListedDirectory;
import io.github.frellibb.adventofcode2022.Day07.Commands.ListedFile;
import io.github.frellibb.adventofcode2022.Day07.FileSystem.Directory;
import io.github.frellibb.adventofcode2022.Day07.FileSystem.File;
import io.github.frellibb.core.BasicResult;
import io.github.frellibb.core.Day;
import io.github.frellibb.core.Result;

import java.util.ArrayList;
import java.util.List;

public class Day07 implements Day {

    private static long MAX_SIZE = 100_000L;
    private static long TOTAL_DISK_SPACE = 70_000_000;
    private static long REQUIRED_SPACE = 30_000_000;

    @Override
    public Result process(List<String> lines) {
        Directory root = parseRoot(lines);
        List<Directory> allDirectories = findAllDirectories(root);

        long part1 = allDirectories.stream()
            .map(Directory::calculateSize)
            .filter(size -> size <= MAX_SIZE)
            .reduce(0L, Long::sum);

        long rootSize = root.calculateSize();
        long availableSpace = TOTAL_DISK_SPACE - rootSize;
        long spaceWeNeedToFree = REQUIRED_SPACE - availableSpace;

        long part2 = allDirectories.stream()
            .map(Directory::calculateSize)
            .filter(size -> size >= spaceWeNeedToFree)
            .min(Long::compareTo)
            .orElseThrow();

        return new BasicResult(part1, part2);
    }

    private Directory parseRoot(List<String> lines) {
        Directory workingDirectory = new Directory("/", new ArrayList<>(), null);

        List<Commands.Command> commands = lines.stream().map(s -> switch (s) {
            case "$ cd /" -> new GoToRoot();
            case "$ cd .." -> new Commands.GoBack();
            case "$ ls" -> new Commands.ListNodes();
            case String st && s.startsWith("$ cd ") -> new GoToDirectory(st.split(" ")[2]);
            case String st && st.startsWith("dir") -> new ListedDirectory(st.split(" ")[1]);
            case String st -> new ListedFile(st.split(" ")[1], Long.parseLong(st.split(" ")[0]));
        }).map(record -> (Command) record).toList();

        for (Command command : commands) {
            workingDirectory = command.processCommand(workingDirectory);
        }

        return new GoToRoot().processCommand(workingDirectory);
    }

    private List<Directory> findAllDirectories(Directory root) {
        ArrayList<Directory> dirs = new ArrayList<>(List.of(root));
        root.children().forEach(node -> {
            if (node instanceof Directory directory) {
                dirs.addAll(findAllDirectories(directory));
            }
        });
        return dirs;
    }

    static class Commands {
        sealed interface Command permits GoToRoot, GoBack, GoToDirectory, ListNodes, ListedDirectory, ListedFile {
            Directory processCommand(Directory workingDirectory);
        }

        record GoToRoot() implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                Directory root = workingDirectory;
                while (root.parent() != null) {
                    root = root.parent();
                }
                return root;
            }
        }

        record GoBack() implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                return workingDirectory.parent();
            }
        }

        record GoToDirectory(String dir) implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                return (Directory) workingDirectory.children().stream().filter(node -> node.name().equals(dir)).findFirst().orElseThrow();
            }
        }

        record ListNodes() implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                return workingDirectory;
            }
        }

        record ListedDirectory(String name) implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                Directory directory = new Directory(name, new ArrayList<>(), workingDirectory);
                workingDirectory.children().add(directory);
                return workingDirectory;
            }

        }

        record ListedFile(String name, long size) implements Command {

            @Override
            public Directory processCommand(Directory workingDirectory) {
                workingDirectory.children().add(new File(name, size));
                return workingDirectory;
            }
        }

    }

    static class FileSystem {
        sealed interface Node permits File, Directory {
            String name();
        }

        record Directory(String name, List<Node> children, Directory parent) implements Node {
            private long calculateSize() {
                return children.stream().map(child -> switch (child) {
                    case File f -> f.size();
                    case Directory dir -> dir.calculateSize();
                }).reduce(0L, Long::sum);
            }

            public String toString() {
                return "dir %s".formatted(name);
            }
        }

        record File(String name, long size) implements Node {
            public String toString() {
                return "file %s %d".formatted(name, size);
            }
        }

    }

}
