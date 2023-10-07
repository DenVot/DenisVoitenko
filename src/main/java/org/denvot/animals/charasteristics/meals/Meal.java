package org.denvot.animals.charasteristics.meals;

public abstract class Meal {
    private boolean isMealAte = false;

    public boolean isMealAte() {
        return isMealAte;
    }

    public void eat() {
        if (isMealAte) {
            throw new RuntimeException("Нельзя съесть еду дважды");
        }

        isMealAte = true;
    }
}
