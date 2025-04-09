package org.shelter.classes;

import org.javatuples.Pair;
import org.shelter.enums.Species;
import org.shelter.interfaces.Persistent;

import java.util.Optional;
import java.util.Vector;

public class Animal implements Persistent {
    private static int max_pk = 0;
    private int pk;

    private final Species specie;
    private String name;
    private String description;

    public Animal(Species specie, String name, String description) {
        this.specie = specie;
        this.name = name;
        this.description = description;

        this.pk = max_pk++;
    }

    public Species getSpecie() { return specie; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public String tableName() {
        return "animals";
    }

    @Override
    public Vector<Pair<String, String>> tableColumns() {
        Vector<Pair<String, String>> columns = new Vector<>();

        columns.add(new Pair<>("name", "varchar(100)"));
        columns.add(new Pair<>("description", "varchar(1000)"));

        return columns;
    }

    @Override
    public Vector<String> tableValues() {
        Vector<String> values = new Vector<>();

        values.add(name);
        values.add(description);

        return values;
    }

    @Override
    public int pk() {
        return pk;
    }

    @Override
    public void load(int pk) {
        Optional<Vector<String>> values = load_table(pk);
        if (values.isEmpty()) { return; }

        name = values.get().elementAt(0);
        description = values.get().elementAt(1);

        this.pk = pk;
    }
}
