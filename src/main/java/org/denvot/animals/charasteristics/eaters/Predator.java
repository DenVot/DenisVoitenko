package org.denvot.animals.charasteristics.eaters;

import org.denvot.animals.charasteristics.meals.Meat;

public interface Predator<TMeat extends Meat> extends Eater<TMeat> {
}
