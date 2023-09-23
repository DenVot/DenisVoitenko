package task2.animals;

import task2.animals.charasteristics.eaters.Herbivorous;
import task2.animals.charasteristics.livingPlaces.LandLiver;
import task2.animals.charasteristics.meals.Grass;

public class Camel implements Herbivorous, LandLiver {
    @Override
    public void Eat(Grass meal) {
        meal.Eat();
        System.out.println("Верблюд ест");
    }

    @Override
    public void Walk() {
        System.out.println("Верблюд идет");
    }
}
