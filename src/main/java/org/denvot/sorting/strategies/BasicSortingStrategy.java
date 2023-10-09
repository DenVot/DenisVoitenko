package org.denvot.sorting.strategies;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicSortingStrategy implements SortingStrategy {
    private final int maxLength;

    public BasicSortingStrategy(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void ensureListLength(List<Integer> array) {
        if (array.size() > maxLength) {
            throw new RuntimeException("Длина массива больше нормы для данного алгоритма");
        }
    }
}
