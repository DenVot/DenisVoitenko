package org.denvot.sorting.strategies;

import java.util.ArrayList;

public class BubbleSortingStrategy extends BasicSortingStrategy implements SortingStrategy {
    public BubbleSortingStrategy(int maxLength) {
        super(maxLength);
    }

    @Override
    public SortingType getSortingType() {
        return SortingType.BubbleSort;
    }

    @Override
    public void sort(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i) > array.get(j)) {
                    int temp = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, temp);
                }
            }
        }
    }
}
