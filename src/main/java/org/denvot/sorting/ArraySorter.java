package org.denvot.sorting;

import org.denvot.sorting.strategies.SortingStrategy;
import org.denvot.sorting.strategies.SortingType;

import java.util.ArrayList;
import java.util.List;

public class ArraySorter {
    private final List<SortingStrategy> sortingStrategies;

    public ArraySorter(List<SortingStrategy> sortingStrategies) {
        this.sortingStrategies = sortingStrategies;
    }

    public List<Integer> sort(List<Integer> array, SortingType sortingType) {
        for (SortingStrategy sortingStrategy : sortingStrategies) {
            if (sortingStrategy.getSortingType() != sortingType) continue;

            sortingStrategy.ensureListLength(array);

            var copyOfArray = new ArrayList<>(array);

            sortingStrategy.sort(copyOfArray);

            return copyOfArray;
        }

        throw new RuntimeException("Не найден нужный алгоритм для сортировки с типом " + sortingType);
    }
}
