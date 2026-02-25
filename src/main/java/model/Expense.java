package model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;


public class Expense {

    private int id = 0;
    private LocalDate date = LocalDate.now();
    private final String description;
    private final double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public Expense(int id, String description, double amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    private Expense(int id, LocalDate date, String description, double amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String toCsv() {
        return String.format("%d,%s,%s,%s\n", this.id, this.date, this.description, this.amount);
    }

    public static  Expense fromCsv(String csvLine) {
        List<String> line = List.of(csvLine.split(","));
        try {
            int id = Integer.parseInt(line.get(0));
            LocalDate date = LocalDate.parse(line.get(1));
            String description = line.get(2).trim();
            double amount = Double.parseDouble(line.get(3));

            return new Expense(id, date, description, amount);
        } catch (NumberFormatException e) {
            System.out.println("Id ou quantidade inválidos: " + csvLine);
            return null;
        } catch (DateTimeException e) {
            System.out.println("Data inválida");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Argumentos não foram passados corretamente!");
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "#  " + id + "   " + date + "   " + description + "   " +  "R$" + amount;
    }
}
