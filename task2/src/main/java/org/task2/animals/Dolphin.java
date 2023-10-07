package org.task2.animals;

import org.task2.animals.charasteristics.eaters.Predator;
import org.task2.animals.charasteristics.livingPlaces.WaterLiver;
import org.task2.animals.charasteristics.meals.Fish;

public class Dolphin implements Predator<Fish>, WaterLiver {
    @Override
    public void eat(Fish meal) {
        meal.eat();
        System.out.println("Дельфин ест");
    }

    @Override
    public void swim() {
        System.out.println("Дельфин плывет");
    }
}
