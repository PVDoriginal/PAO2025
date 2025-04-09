package org.shelter;

import org.shelter.classes.*;
import org.shelter.enums.Species;
import org.shelter.interfaces.Persistent;
import org.shelter.singletons.JDBCManager;
import org.shelter.utility.Initialization;
import org.shelter.utility.Logs;
import org.shelter.utility.Services;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Main {

    static Vector<Shelter> shelters;
    static Vector<Animal> animals;
    static Vector<Staff> staff;
    static Vector<Person> people;
    static Vector<Food> food;
    static Vector<Vaccine> vaccines;
    static Set<Payment> payments;

    public static void main(String[] args) throws IOException {
        initLists();
        testServices();
        //saveToDatabase();
        testJDBC();

        Logs.save_logs();
    }

    public static void testJDBC() {

        // singleton
        JDBCManager manager = JDBCManager.getInstance();

        // check if tables exist
        manager.checkIfTableExists("shelter");
        manager.checkIfTableExists("staff");
        manager.checkIfTableExists("food");

        Shelter shelter1 = new Shelter("", "");
        Shelter shelter2 = new Shelter("", "");

        // load 2 shelters from the database

        shelter1.load(0);
        shelter2.load(1);

        // print them

        System.out.println(shelter1);
        System.out.println(shelter2);

        // change a shelter's name

        shelter1.setName("newShelterName");

        // update it in the database

        shelter1.update();
    }

    public static void saveToDatabase() {

        shelters.elementAt(0).drop();
        shelters.elementAt(0).createIfNotExists();
        for (Shelter shelter : shelters) {
            shelter.insert();
        }

        staff.elementAt(0).drop();
        staff.elementAt(0).createIfNotExists();
        for (Staff staff1 : staff) {
            staff1.insert();
        }

        animals.elementAt(0).drop();
        animals.elementAt(0).createIfNotExists();
        for (Animal animal : animals) {
            animal.insert();
        }

        payments.stream().findFirst().ifPresent(Persistent::drop);
        payments.stream().findFirst().ifPresent(Persistent::createIfNotExists);
        for (Payment payment : payments) {
            payment.insert();
        }
    }

    public static void testServices() {
        Services.personRescuesAnimal(people.elementAt(0), animals.elementAt(0), shelters.elementAt(0));
        Services.personRescuesAnimal(people.elementAt(0), animals.elementAt(1), shelters.elementAt(0));
        Services.personRescuesAnimal(people.elementAt(1), animals.elementAt(2), shelters.elementAt(1));
        Services.personRescuesAnimal(people.elementAt(1), animals.elementAt(3), shelters.elementAt(0));
        Services.personRescuesAnimal(people.elementAt(0), animals.elementAt(4), shelters.elementAt(2));
        Services.personRescuesAnimal(people.elementAt(1), animals.elementAt(5), shelters.elementAt(2));
        Services.personRescuesAnimal(people.elementAt(0), animals.elementAt(6), shelters.elementAt(0));
        Services.personRescuesAnimal(people.elementAt(0), animals.elementAt(7), shelters.elementAt(0));
        System.out.println();

        Services.personAdoptsAnimal(people.elementAt(0), animals.elementAt(0), shelters.elementAt(0));
        Services.personAdoptsAnimal(people.elementAt(0), animals.elementAt(2), shelters.elementAt(0));
        System.out.println();

        Services.shelterGetsProduct(shelters.elementAt(0), (Product) vaccines.elementAt(0));
        Services.personBuysProduct(people.elementAt(0), (Product) vaccines.elementAt(0), shelters.elementAt(0));
        System.out.println();

        Services.getAllAnimalsInShelter(shelters.elementAt(0));
        System.out.println();

        Services.getSortedPayments(staff.elementAt(0));
        System.out.println();

        Services.checkIfFood((Product) food.elementAt(0));
        Services.checkIfFood((Product) vaccines.elementAt(0));
        System.out.println();

        Services.hirePerson(shelters.elementAt(1), people.elementAt(1), 1000);
        System.out.println();

        Services.paySalary(staff.elementAt(0));
        System.out.println();

        Services.getAllAnimalsOfSpecies(shelters, Species.Cat);
        System.out.println();

        Services.transferAnimal(animals.elementAt(7), shelters.elementAt(0), shelters.elementAt(1));
        System.out.println();
    }

    public static void initLists() {
        shelters = Initialization.createShelters();
        animals = Initialization.createAnimals();
        staff = Initialization.createStaff();
        people = Initialization.createPeople();
        food = Initialization.createFood();
        vaccines = Initialization.createVaccines();

        payments = new HashSet<>();
        for (Staff staff1 : staff) {
            payments.addAll(staff1.getPayments());
        }
    }
}