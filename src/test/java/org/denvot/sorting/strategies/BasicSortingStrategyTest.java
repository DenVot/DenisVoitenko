package org.denvot.sorting.strategies;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicSortingStrategyTest {
    @Test
    public void Should_Not_Throw_RuntimeException_Because_Array_Is_Too_Big() {
        var sortingStrategy = new BasicSortingStrategy(5) {

            @Override
            public SortingType getSortingType() {
                return null;
            }

            @Override
            public void sort(ArrayList<Integer> array) {

            }
        };

        assertDoesNotThrow(() -> sortingStrategy.ensureListLength(List.of(3, 2, 6)));
    }

    @Test
    public void Should_Throw_RuntimeException_Because_Array_Is_Too_Big() {
        var sortingStrategy = new BasicSortingStrategy(5) {

            @Override
            public SortingType getSortingType() {
                return null;
            }

            @Override
            public void sort(ArrayList<Integer> array) {

            }
        };

        assertThrows(RuntimeException.class,
                () -> sortingStrategy.ensureListLength(List.of(3, 2, 3, 4, 5, 6)));
    }
}
