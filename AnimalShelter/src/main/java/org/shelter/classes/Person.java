package org.shelter.classes;

import org.shelter.interfaces.Persistent;

import java.util.Objects;
import java.util.Vector;

public class Person {
    private String name;
    private String surname;

    private String email;

    private Vector<Animal> adopted = new Vector<>();
    private Vector<Animal> rescued = new Vector<>();

    private Vector<Product> products = new Vector<>();

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Person(Person person) {
        this.name = person.name;
        this.surname = person.surname;
        this.email = person.email;
        this.adopted = (Vector<Animal>) person.adopted.clone();
        this.rescued = (Vector<Animal>) person.rescued.clone();
        this.products = (Vector<Product>) person.products.clone();
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public Vector<Animal> getAdopted() { return adopted; }
    public Vector<Animal> getRescued() { return rescued; }
    public Vector<Product> getProducts() { return products; }

    public void setEmail(String email) { this.email = email; }

    public void rescueAnimal(Animal animal) {
        rescued.add(animal);
    }

    public void adoptAnimal(Animal animal) {
        adopted.add(animal);
    }

    public void buyProduct(Product product) {
        products.add(product);
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
