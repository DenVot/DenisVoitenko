package org.denvot.animals;

import org.denvot.animals.charasteristics.eaters.Predator;
import org.denvot.animals.charasteristics.livingPlaces.WaterLiver;
import org.denvot.animals.charasteristics.meals.Fish;

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
