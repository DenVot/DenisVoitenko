package org.task2.animals;

import org.task2.animals.charasteristics.eaters.Predator;
import org.task2.animals.charasteristics.livingPlaces.LandLiver;
import org.task2.animals.charasteristics.meals.Beef;

public class Tiger implements Predator<Beef>, LandLiver {
    @Override
    public void Eat(Beef meal) {
        meal.Eat();
        System.out.println("Тигр ест");
    }

    @Override
    public void Walk() {
        System.out.println("Тигр идет");
    }
}
