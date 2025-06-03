package org.shelter.classes;

import org.javatuples.Pair;
import org.shelter.interfaces.Persistent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;

public class Shelter implements Persistent {
    private static int max_pk = 0;
    private int pk;

    private String name;
    private String address;

    private Vector<Animal>  animals = new Vector<>();
    private Vector<Product> products = new Vector<>();

    private Vector<Staff> staff = new Vector<Staff>();

    public Shelter(String name, String address) {
        this.name = name;
        this.address = address;
        pk = max_pk++;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public Vector<Animal> getAnimals() { return animals; }

    public void setName(String name) { this.name = name; }

    public void rescueAnimal(Animal animal, Person person) {
        animals.add(animal);
        person.adoptAnimal(animal);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void adoptAnimal(Animal animal, Person person) {
        if (!animals.contains(animal)) {
            System.out.println(animal.getName() + " not in shelter");
            return;
        }

        animals.remove(animal);
        person.adoptAnimal(animal);
    }

    public void transferAnimal(Animal animal, Shelter shelter) {
        if (!animals.contains(animal)) {
            System.out.println("Animal " + animal.getName() + " not in shelter");
            return;
        }
        animals.remove(animal);

        if (shelter.animals.contains(animal)) {
            System.out.println("Animal " + animal.getName() + " is already in the other shelter");
            return;
        }
        shelter.animals.add(animal);
    }

    public void buyProduct(Product product, Person person) {
        if(!products.contains(product)) {
            System.out.println("Product " + product + " not in shelter");
            return;
        }

        products.remove(product);

        if(person.hasProduct(product)) {
            System.out.println("Person already has " + product);
            return;
        }

        person.buyProduct(product);
    }

    public void hireStaff(Staff staff) {
        this.staff.add(staff);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(name, shelter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String tableName() {
        return "shelter";
    }

    @Override
    public Vector<Pair<String, String>> tableColumns() {
        Vector<Pair<String, String>> columns = new Vector<>();

        columns.add(new Pair<>("name", "varchar(100)"));
        columns.add(new Pair<>("address", "varchar(1000)"));

        return columns;
    }

    @Override
    public Vector<String> tableValues() {
        Vector<String> values = new Vector<>();

        values.add(name);
        values.add(address);

        return values;

    }

    @Override
    public int pk() {
        return pk;
    }

    @Override
    public void load(int pk, Connection conn) {
        Optional<Vector<String>> values = load_table(pk, conn);
        if (values.isEmpty()) { return; }

        name = values.get().elementAt(0);
        address = values.get().elementAt(1);

        this.pk = pk;
    }
}

