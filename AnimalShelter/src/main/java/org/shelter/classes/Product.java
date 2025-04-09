package org.shelter.classes;

import org.shelter.enums.Species;

import java.util.Vector;

public abstract sealed class Product permits Food, Vaccine {
    private String name;
    private final Vector<Species> allowedSpecies;
    private int price;
    private int quantity;

    Product (String name, Vector<Species> allowedSpecies, int price, int quantity) {
        this.allowedSpecies = allowedSpecies;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
