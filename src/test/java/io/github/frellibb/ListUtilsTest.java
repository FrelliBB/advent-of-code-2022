package io.github.frellibb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListUtilsTest {

    @Test
    void splitListBySeperator() {
        List<Integer> input = List.of(1, 2, 3, 0, 4, 0, 5, 6);
        List<List<Integer>> lists = ListUtils.splitListByPredicate(input, integer -> integer == 0);
        assertThat(lists).containsExactlyInAnyOrder(
            List.of(1, 2, 3),
            List.of(4),
            List.of(5, 6)
        );
    }
}