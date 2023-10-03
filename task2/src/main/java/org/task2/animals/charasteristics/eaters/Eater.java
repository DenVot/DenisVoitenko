package org.task2.animals.charasteristics.eaters;

import org.task2.animals.charasteristics.meals.Meal;

public interface Eater<TMeal extends Meal> {
    void eat(TMeal meal);
}
