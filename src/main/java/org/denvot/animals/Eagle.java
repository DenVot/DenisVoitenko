package org.denvot.animals;

import org.denvot.animals.charasteristics.eaters.Predator;
import org.denvot.animals.charasteristics.livingPlaces.AirLiver;
import org.denvot.animals.charasteristics.meals.Beef;

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
