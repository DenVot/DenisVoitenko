package org.task2;

import org.task2.animals.Camel;
import org.task2.animals.Horse;
import org.task2.animals.charasteristics.meals.Grass;

public class App
{
    public static void main( String[] args )
    {
        Grass grassForCamel = new Grass();
        Grass grassForHorse = new Grass();

        Camel camel = new Camel();
        Horse horse = new Horse();

        camel.eat(grassForCamel);
        horse.eat(grassForHorse);

        try {
            horse.eat(grassForHorse);
        } catch (Exception e) {
            System.out.println("Мы не можем есть уже съеденную еду");
        }
    }
}
