package org.shelter.utility;

import org.shelter.classes.*;
import org.shelter.enums.Species;

import java.sql.Date;
import java.util.Comparator;
import java.util.Vector;

public class Services {

    public static void personRescuesAnimal(Person person, Animal animal, Shelter shelter) {
        Logs.log(person + " rescued " + animal + " in " + shelter);
        shelter.rescueAnimal(animal, person);
    }

    public static void personAdoptsAnimal(Person person, Animal animal, Shelter shelter) {
        Logs.log(person + " adopted " + animal + " from " + shelter);
        shelter.adoptAnimal(animal, person);
    }

    public static void personBuysProduct(Person person, Product product, Shelter shelter) {
        Logs.log(person + " buys " + product + " from " + shelter);
        shelter.buyProduct(product, person);
    }

    public static void shelterGetsProduct(Shelter shelter, Product product) {
        Logs.log(shelter + " gets " + product);
        shelter.addProduct(product);
    }

    public static Vector<Animal> getAllAnimalsInShelter(Shelter shelter) {
        Logs.log("getting all animals in " + shelter + ":");
        for (Animal animal : shelter.getAnimals()) {
            Logs.log(animal.toString());
        }
        return shelter.getAnimals();
    }

    public static Vector<Payment> getSortedPayments (Staff staff) {
        Logs.log("getting sorted payments for " + staff + ":");

        Comparator<Payment> byAmount = Comparator.comparing(Payment::getAmount);
        Vector<Payment> payments = staff.getPayments();
        payments.sort(byAmount);

        for (Payment payment : payments) {
            Logs.log(payment.toString());
        }

        return payments;
    }

    public static Boolean checkIfFood (Product product) {
        Logs.log("checking if " + product + " is food");
        return product instanceof Food;
    }

    public static void hirePerson(Shelter shelter, Person person, int salary) {
        Logs.log("hiring " + person + " in " + shelter);
        Staff staff = new Staff(person, salary);
        shelter.hireStaff(staff);
    }

    public static void paySalary(Staff staff) {
        Logs.log("paying " + staff + " their salary");
        staff.addPayment(new Payment(staff.getSalary()));
    }

    public static Vector<Animal> getAllAnimalsOfSpecies(Vector<Shelter> shelters, Species species) {
        Logs.log("getting all animals of " + species + ":");
        Vector<Animal> animals = new Vector<>();

        for (Shelter shelter: shelters) {
            for (Animal animal: shelter.getAnimals()) {
                if (animal.getSpecie() == species) {
                    System.out.println(animal + " in shelter " + shelter);
                    animals.add(animal);
                }
            }
        }

        return  animals;
    }

    public static void transferAnimal(Animal animal, Shelter from, Shelter to) {
        Logs.log("transfering " + animal + " from " + from + " to " + to);
        from.transferAnimal(animal, to);
    }
}
