package org.task2.animals.charasteristics.meals;

public abstract class Meal {
    private boolean isMealAte = false;

    public boolean isMealAte() {
        return isMealAte;
    }

    public void Eat() {
        if (isMealAte) {
            throw new RuntimeException("Нельзя съесть еду дважды");
        }

        isMealAte = true;
    }
}
