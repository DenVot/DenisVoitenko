package org.denvot.sorting.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergingSortingStrategy extends BasicSortingStrategy implements SortingStrategy {
    public MergingSortingStrategy(int maxLength) {
        super(maxLength);
    }

    @Override
    public SortingType getSortingType() {
        return SortingType.MergingSort;
    }

    @Override
    public void sort(ArrayList<Integer> array) {
        Collections.sort(array);
    }
}
