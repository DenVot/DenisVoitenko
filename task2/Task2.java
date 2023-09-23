package task2;

import task2.animals.Camel;
import task2.animals.Horse;
import task2.animals.charasteristics.meals.Grass;

public class Task2 {
    public static void main(String[] args) {
        Grass grassForCamel = new Grass();
        Grass grassForHorse = new Grass();

        Camel camel = new Camel();
        Horse horse = new Horse();

        camel.Eat(grassForCamel);
        horse.Eat(grassForHorse);

        try {
            horse.Eat(grassForHorse);
        } catch (Exception e) {
            System.out.println("Мы не можем есть уже съеденную еду");
        }
    }
}
