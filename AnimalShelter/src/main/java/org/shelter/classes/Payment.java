package org.shelter.classes;

import org.javatuples.Pair;
import org.shelter.interfaces.Persistent;

import java.sql.Date;
import java.util.Comparator;
import java.util.Optional;
import java.util.Vector;

public class Payment implements Comparable<Payment>, Persistent {
    private static int max_pk = 0;
    private int pk;

    private int amount;

    public Payment(int amount) {
        this.amount = amount;
        this.pk = max_pk;
    }

    public int getAmount() { return amount; }


    @Override
    public int compareTo(Payment p) {
        return amount - p.amount;
    }

    @Override
    public String tableName() {
        return "payments";
    }

    @Override
    public Vector<Pair<String, String>> tableColumns() {
        Vector<Pair<String, String>> columns = new Vector<>();

        columns.add(new Pair<>("amount", "varchar(100)"));

        return columns;
    }

    @Override
    public Vector<String> tableValues() {
        Vector<String> values = new Vector<>();

        values.add(String.valueOf(amount));

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

        amount = Integer.parseInt(values.get().elementAt(0));
        this.pk = pk;
    }
}