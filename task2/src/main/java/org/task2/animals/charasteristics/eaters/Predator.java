package org.task2.animals.charasteristics.eaters;

import org.task2.animals.charasteristics.meals.Meat;

public interface Predator<TMeat extends Meat> extends Eater<TMeat> {
}
