package org.task2.animals;

import org.task2.animals.charasteristics.eaters.Herbivorous;
import org.task2.animals.charasteristics.livingPlaces.LandLiver;
import org.task2.animals.charasteristics.meals.Grass;

public class Horse implements Herbivorous, LandLiver {
    @Override
    public void eat(Grass meal) {
        meal.eat();
        System.out.println("Лошадь ест");
    }

    @Override
    public void walk() {
        System.out.println("Лошадь идет");
    }
}
