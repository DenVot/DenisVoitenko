package org.denvot.animals.charasteristics.eaters;

import org.denvot.animals.charasteristics.meals.Meal;

public interface Eater<TMeal extends Meal> {
    void eat(TMeal meal);
}
