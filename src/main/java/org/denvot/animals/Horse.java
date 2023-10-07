package org.denvot.animals;

import org.denvot.animals.charasteristics.eaters.Herbivorous;
import org.denvot.animals.charasteristics.livingPlaces.LandLiver;
import org.denvot.animals.charasteristics.meals.Grass;

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
