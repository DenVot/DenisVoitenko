package org.task2.animals;

import org.task2.animals.charasteristics.eaters.Predator;
import org.task2.animals.charasteristics.livingPlaces.AirLiver;
import org.task2.animals.charasteristics.meals.Beef;

public class Eagle implements Predator<Beef>, AirLiver {
    @Override
    public void Eat(Beef meal) {
        meal.Eat();
        System.out.println("Орел ест");
    }

    @Override
    public void Fly() {
        System.out.println("Орел летит");
    }
}
