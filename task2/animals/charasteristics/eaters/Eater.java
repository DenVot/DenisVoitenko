package task2.animals.charasteristics.eaters;

import task2.animals.charasteristics.meals.Meal;

public interface Eater<TMeal extends Meal> {
    void Eat(TMeal meal);
}
