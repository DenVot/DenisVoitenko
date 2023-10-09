package org.denvot.sorting.strategies;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortingStrategyTest {
    @Test
    public void Should_Be_BubbleSorting_Type() {
        var strategy = new BubbleSortingStrategy(5);

        assertEquals(strategy.getSortingType(), SortingType.BubbleSort);
    }

    @Test
    public void Sort_Test() {
        var testData = new ArrayList<>(List.of(3, 2, 1, 4, 5, 6));
        var bubbleSortStrategy = new BubbleSortingStrategy(7);

        bubbleSortStrategy.sort(testData);

        Integer[] expectedResult = {1, 2, 3, 4, 5, 6};

        assertArrayEquals(expectedResult, testData.toArray());
    }
}