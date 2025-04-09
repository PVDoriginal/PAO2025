package org.shelter.utility;

import org.shelter.classes.*;
import org.shelter.enums.Species;
import org.shelter.enums.VaccineType;

import java.util.Vector;

public class Initialization {

    public static Vector<Shelter> createShelters() {
        Vector<Shelter> shelters = new Vector<Shelter>();

        shelters.add(new Shelter("shelter1", "Bucuresti"));
        shelters.add(new Shelter("shelter2", "Buzau"));
        shelters.add(new Shelter("shelter3", "Brasov"));
        shelters.add(new Shelter("shelter4", "Constanta"));

        return shelters;
    }

    public static Vector<Animal> createAnimals() {
        Vector<Animal> animals = new Vector<Animal>();

        animals.add(new Animal(Species.Dog, "Dog1", "desc"));
        animals.add(new Animal(Species.Dog, "Dog2", "desc"));
        animals.add(new Animal(Species.Dog, "Dog3", "desc"));

        animals.add(new Animal(Species.Cat, "Cat1", "desc"));
        animals.add(new Animal(Species.Cat, "Cat2", "desc"));

        animals.add(new Animal(Species.Fish, "Fish1", "desc"));
        animals.add(new Animal(Species.Fish, "Fish2", "desc"));
        animals.add(new Animal(Species.Fish, "Fish3", "desc"));

        animals.add(new Animal(Species.Hamster, "Hamster1", "desc"));
        animals.add(new Animal(Species.Hamster, "Hamster2", "desc"));

        return animals;
    }

    public static Vector<Person> createPeople() {
        Vector<Person> people = new Vector<Person>();

        people.add(new Person("Gigel", "A", "gigel@gmail.com"));
        people.add(new Person("Donald", "B", "donald@gmail.com"));

        return people;
    }

    public static Vector<Staff> createStaff() {
        Vector<Staff> staff = new Vector<Staff>();

        staff.add(new Staff("Madalina", "C", "madalina@gmail.com", 1200));
        staff.add(new Staff("John", "D", "john@gmail.com", 1000));

        Services.paySalary(staff.elementAt(0));

        return staff;
    }

    public static Vector<Food> createFood() {
        Vector<Food> food = new Vector<Food>();

        Vector<Species> species = new Vector<>();
        species.add(Species.Dog);

        food.add(new Food("Dog food", species, 10, 5, 100));

        return food;
    }

    public static Vector<Vaccine> createVaccines() {
        Vector<Vaccine> vaccines = new Vector<Vaccine>();

        Vector<Species> species = new Vector<>();
        species.add(Species.Cat);

        vaccines.add(new Vaccine("Vaccine", species, 10, 5, 365, VaccineType.Hepatities));

        return vaccines;
    }
}
