package io.github.frellibb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class ListUtils {

    public static <T> List<List<T>> splitListByPredicate(List<T> list, Predicate<T> predicate) {
        int[] indexes = Stream.of(IntStream.of(-1), // start of list
            IntStream.range(0, list.size()).filter(i -> predicate.test(list.get(i))), // indices of lines that match predicate
            IntStream.of(list.size()) // end of list
        ).flatMapToInt(s -> s).toArray();

        return IntStream.range(0, indexes.length - 1).mapToObj(i -> list.subList(indexes[i] + 1, indexes[i + 1])).toList();
    }

    public static <I, O> List<O> mapListEntries(List<I> list, Function<I, O> mapper) {
        return list.stream().map(mapper).toList();
    }

    public static <T> List<List<T>> chunk(List<T> list, int chunkSize) {
        AtomicInteger atomicInteger = new AtomicInteger();
        return new ArrayList<>(list.stream().collect(groupingBy(t -> atomicInteger.getAndIncrement() / chunkSize)).values());
    }

}

