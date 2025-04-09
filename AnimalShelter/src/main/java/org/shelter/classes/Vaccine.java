package org.shelter.classes;

import org.shelter.enums.Species;
import org.shelter.enums.VaccineType;

import java.util.Vector;

public final class Vaccine extends Product {
    private int Duration;
    private VaccineType VaccineType;

    public Vaccine(String name, Vector<Species> allowedSpecies, int price, int quantity, int duration, VaccineType vaccineType) {
        super(name, allowedSpecies, price, quantity);
        Duration = duration;
        VaccineType = vaccineType;
    }
}
