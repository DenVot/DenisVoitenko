package org.task2.animals;

import org.task2.animals.charasteristics.eaters.Predator;
import org.task2.animals.charasteristics.livingPlaces.AirLiver;
import org.task2.animals.charasteristics.meals.Beef;

public class Eagle implements Predator<Beef>, AirLiver {
    @Override
    public void eat(Beef meal) {
        meal.eat();
        System.out.println("Орел ест");
    }

    @Override
    public void fly() {
        System.out.println("Орел летит");
    }
}
