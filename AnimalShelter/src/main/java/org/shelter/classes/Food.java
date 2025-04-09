package org.shelter.classes;

import org.shelter.enums.Species;

import java.util.Vector;

public final class Food extends Product {
    private int calories;

    public Food(String name, Vector<Species> allowedSpecies, int price, int quantity, int calories) {
        super(name, allowedSpecies, price, quantity);
        this.calories = calories;
    }

    public int getCalories () { return calories; }
}
