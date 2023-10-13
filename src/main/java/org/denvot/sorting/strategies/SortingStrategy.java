package org.denvot.sorting.strategies;

import java.util.ArrayList;
import java.util.List;

public interface SortingStrategy {
    SortingType getSortingType();

    /**
     * @throws RuntimeException, если длина массива больше заданной
     */
    void ensureListLength(List<Integer> array);
    void sort(ArrayList<Integer> array);
}
