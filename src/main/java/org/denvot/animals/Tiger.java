package org.denvot.animals;

import org.denvot.animals.charasteristics.eaters.Predator;
import org.denvot.animals.charasteristics.livingPlaces.LandLiver;
import org.denvot.animals.charasteristics.meals.Beef;

public class Tiger implements Predator<Beef>, LandLiver {
    @Override
    public void eat(Beef meal) {
        meal.eat();
        System.out.println("Тигр ест");
    }

    @Override
    public void walk() {
        System.out.println("Тигр идет");
    }
}
