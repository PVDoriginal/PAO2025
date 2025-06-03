package org.shelter.classes;

import org.javatuples.Pair;
import org.shelter.interfaces.Persistent;

import java.sql.Connection;
import java.util.Optional;
import java.util.Vector;

public class Staff extends Person implements Persistent {
    private static int max_pk = 0;
    private int pk;

    private int salary;
    private Vector<Payment> payments = new Vector<>();

    public Staff(String firstName, String lastName, String email, int salary) {
        super(firstName, lastName, email);
        this.salary = salary;
        pk = max_pk++;
    }

    public Staff(Person person, int salary) {
        super(person);
        this.salary = salary;
        pk = max_pk++;
    }

    public int getSalary() { return salary; }
    public void setSalary(int newSalary) { this.salary = newSalary; }

    public Vector<Payment> getPayments() { return payments; }
    public void addPayment(Payment payment) { this.payments.add(payment); }

    @Override
    public String tableName() {
        return "staff";
    }

    @Override
    public Vector<Pair<String, String>> tableColumns() {
        Vector<Pair<String, String>> columns = new Vector<>();

        columns.add(new Pair<>("name", "varchar(100)"));
        columns.add(new Pair<>("salary", "varchar(100)"));

        return columns;
    }

    @Override
    public Vector<String> tableValues() {
        Vector<String> values = new Vector<>();

        values.add(getName());
        values.add(String.valueOf(salary));

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

        setName(values.get().elementAt(0));
        salary = Integer.parseInt(values.get().elementAt(1));

        this.pk = pk;
    }
}
