package task2.animals;

import task2.animals.charasteristics.eaters.Predator;
import task2.animals.charasteristics.livingPlaces.WaterLiver;
import task2.animals.charasteristics.meals.Fish;

public class Dolphin implements Predator<Fish>, WaterLiver {
    @Override
    public void Eat(Fish meal) {
        meal.Eat();
        System.out.println("Дельфин ест");
    }

    @Override
    public void Swim() {
        System.out.println("Дельфин плывет");
    }
}
