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
import java.util.Scanner;

public class Main {

    static Vector<Shelter> shelters;
    static Vector<Animal> animals;
    static Vector<Staff> staff;
    static Vector<Person> people;
    static Vector<Food> food;
    static Vector<Vaccine> vaccines;
    static Vector<Product> products;
    static Set<Payment> payments;

    public static void main(String[] args) throws IOException {
        initLists();

        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);

//        testServices();
//        saveToDatabase();
//        testJDBC();

        Logs.save_logs();
    }

    public static void mainMenu(Scanner scanner) {

        while (true) {
            System.out.println("Choose a type of operation:");
            System.out.println("1. Java Services");
            System.out.println("2. Database Services");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            if (choice == 3) {
                break;
            }

            switch (choice) {
                case 1:
                    javaMenu(scanner);
                    break;
                case 2:
                    dbMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

        }

    }


    public static void dbMenu(Scanner scanner) {

        JDBCManager manager = JDBCManager.getInstance();

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Check if table exists");
            System.out.println("2. Save all to database");
            System.out.println("3. Load a shelter from the database");
            System.out.println("4. Save a shelter to database");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            if (choice == 5) {
                break;
            }

            int pk;
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter table name:");
                        String tableName = scanner.next();
                        manager.checkIfTableExists(tableName);
                        break;
                    case 2:
                        saveToDatabase(manager.getConnection());
                        break;
                    case 3:
                        System.out.println("Enter pk:");
                        pk = scanner.nextInt();
                        Shelter shelter1 = new Shelter("", "");
                        shelter1.load(pk, manager.getConnection());
                        shelters.add(shelter1);
                        break;
                    case 4:
                        System.out.println("Enter shelter index:");
                        int index = scanner.nextInt();
                        shelters.elementAt(index).update(manager.getConnection());
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input");
            }

        }

    }


    public static void javaMenu(Scanner scanner) {

        while (true) {

            System.out.println("Choose an operation:");
            System.out.println("1. See All Data");
            System.out.println("2. Register Saved Animal");
            System.out.println("3. Register Adoption");
            System.out.println("4. Buy Product");
            System.out.println("5. Add Product to Shelter");
            System.out.println("6. See Animals in Shelter");
            System.out.println("7. See All Payments for Staff (Sorted)");
            System.out.println("8. Check if Product is Food");
            System.out.println("9. Hire Person");
            System.out.println("10. Pay Monthly Salary to Staff");

            System.out.println("11. Exit");

            int choice = scanner.nextInt();

            if (choice == 11) {
                break;
            }

            int index1, index2, index3;

            try {
                switch (choice) {
                    case 1:
                        printLists();
                        break;
                    case 2:
                        System.out.println("Enter the index of the animal:");
                        index1 = scanner.nextInt();

                        System.out.println("Enter the index of the person:");
                        index2 = scanner.nextInt();

                        System.out.println("Enter the index of the shelter:");
                        index3 = scanner.nextInt();

                        Services.personRescuesAnimal(people.elementAt(index2), animals.elementAt(index1), shelters.elementAt(index3));
                        break;
                    case 3:
                        System.out.println("Enter the index of the animal:");
                        index1 = scanner.nextInt();

                        System.out.println("Enter the index of the person:");
                        index2 = scanner.nextInt();

                        System.out.println("Enter the index of the shelter:");
                        index3 = scanner.nextInt();

                        Services.personAdoptsAnimal(people.elementAt(index2), animals.elementAt(index1), shelters.elementAt(index3));
                        break;
                    case 4:
                        System.out.println("Enter the index of the product:");
                        index1 = scanner.nextInt();

                        System.out.println("Enter the index of the person:");
                        index2 = scanner.nextInt();

                        System.out.println("Enter the index of the shelter:");
                        index3 = scanner.nextInt();

                        Services.personBuysProduct(people.elementAt(index2), products.elementAt(index1), shelters.elementAt(index3));
                        break;
                    case 5:
                        System.out.println("Enter the index of the product:");
                        index1 = scanner.nextInt();

                        System.out.println("Enter the index of the shelter:");
                        index2 = scanner.nextInt();

                        Services.shelterGetsProduct(shelters.elementAt(index2), products.elementAt(index1));
                        break;
                    case 6:
                        System.out.println("Enter the index of the shelter:");
                        index1 = scanner.nextInt();

                        Services.getAllAnimalsInShelter(shelters.elementAt(index1));
                        break;
                    case 7:
                        System.out.println("Enter the index of the staff:");
                        index1 = scanner.nextInt();

                        Services.getSortedPayments(staff.elementAt(index1));
                        break;
                    case 8:
                        System.out.println("Enter the index of the product:");
                        index1 = scanner.nextInt();

                        Services.checkIfFood(products.elementAt(index1));
                        break;
                    case 9:
                        System.out.println("Enter the index of the person:");
                        index1 = scanner.nextInt();

                        System.out.println("Enter the index of the shelter:");
                        index2 = scanner.nextInt();

                        System.out.println("Enter salary:");
                        int salary = scanner.nextInt();

                        Services.hirePerson(shelters.elementAt(index2), people.elementAt(index1), salary);
                        break;
                    case 10:
                        System.out.println("Enter the index of the staff:");
                        index1 = scanner.nextInt();

                        Services.paySalary(staff.elementAt(index1));
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Input error!");
            }

        }

    }

//
//    public static void testJDBC() {
//
//        // singleton
//        JDBCManager manager = JDBCManager.getInstance();
//
//
//        // check if tables exist
//        manager.checkIfTableExists("shelter");
//        manager.checkIfTableExists("staff");
//        manager.checkIfTableExists("food");
//
//        Shelter shelter1 = new Shelter("", "");
//        Shelter shelter2 = new Shelter("", "");
//
//        // load 2 shelters from the database
//
//        shelter1.load(0);
//        shelter2.load(1);
//
//        // print them
//
//        System.out.println(shelter1);
//        System.out.println(shelter2);
//
//        // change a shelter's name
//
//        shelter1.setName("newShelterName");
//
//        // update it in the database
//
//        shelter1.update();
//    }

    public static void saveToDatabase(Connection conn) {

        shelters.elementAt(0).drop(conn);
        shelters.elementAt(0).createIfNotExists(conn);
        for (Shelter shelter : shelters) {
            shelter.insert(conn);
        }

        staff.elementAt(0).drop(conn);
        staff.elementAt(0).createIfNotExists(conn);
        for (Staff staff1 : staff) {
            staff1.insert(conn);
        }

        animals.elementAt(0).drop(conn);
        animals.elementAt(0).createIfNotExists(conn);
        for (Animal animal : animals) {
            animal.insert(conn);
        }

        payments.stream().findFirst().ifPresent(payment -> {payment.drop(conn);});
        payments.stream().findFirst().ifPresent(payment -> {payment.createIfNotExists(conn);});
        for (Payment payment : payments) {
            payment.insert(conn);
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

        products = new Vector<>();
        products.addAll(food);
        products.addAll(vaccines);
    }

    public static void printLists() {
        System.out.println("shelters");
        System.out.println("----------------------");
        for (int i = 0; i < shelters.size(); i++) {
            System.out.println(i + ": " + shelters.elementAt(i));
        }
        System.out.println();
        System.out.println();

        System.out.println("animals");
        System.out.println("----------------------");
        for (int i = 0; i < animals.size(); i++) {
            System.out.println(i + ": " + animals.elementAt(i));
        }
        System.out.println();
        System.out.println();

        System.out.println("staff");
        System.out.println("----------------------");
        for (int i = 0; i < staff.size(); i++) {
            System.out.println(i + ": " + staff.elementAt(i));
        }
        System.out.println();
        System.out.println();

        System.out.println("people");
        System.out.println("----------------------");
        for (int i = 0; i < people.size(); i++) {
            System.out.println(i + ": " + people.elementAt(i));
        }
        System.out.println();
        System.out.println();

        System.out.println("products");
        System.out.println("----------------------");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + ": " + products.elementAt(i));
        }
        System.out.println();
        System.out.println();
    }
}