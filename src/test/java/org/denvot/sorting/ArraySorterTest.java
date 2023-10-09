package org.denvot.sorting;

import org.denvot.sorting.strategies.BasicSortingStrategy;
import org.denvot.sorting.strategies.SortingType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArraySorterTest {
    @Test
    public void Sort_Should_Return_Array_Without_Exceptions() {
        var defSortingStrategy = new BasicSortingStrategy(5) {

            @Override
            public SortingType getSortingType() {
                return SortingType.MergingSort;
            }

            @Override
            public void sort(ArrayList<Integer> array) {

            }
        };

        var sorter = new ArraySorter(List.of(defSortingStrategy));
        var array = List.of(3, 2);

        var result = sorter.sort(array, SortingType.MergingSort);

        assertEquals(result.get(0), 3);
        assertEquals(result.get(1), 2);
    }

    @Test
    public void Sort_Should_Throw_RuntimeException_Because_No_Algorithm_Found_With_This_Type() {
        var sorter = new ArraySorter(new ArrayList<>());
        var array = List.of(1, 2);

        assertThrows(RuntimeException.class,
                () -> sorter.sort(array, SortingType.BubbleSort));
    }

    @Test
    public void Sort_Should_Throw_RuntimeException_Because_No_Algorithm_Found_With_Max_Len() {
        var defSortingStrategy = new BasicSortingStrategy(5) {

            @Override
            public SortingType getSortingType() {
                return SortingType.MergingSort;
            }

            @Override
            public void sort(ArrayList<Integer> array) {

            }
        };

        var sorter = new ArraySorter(List.of(defSortingStrategy));
        var array = List.of(3, 2, 4, 6, 6, 6);

        assertThrows(RuntimeException.class,
                () -> sorter.sort(array, SortingType.MergingSort));
    }
}
