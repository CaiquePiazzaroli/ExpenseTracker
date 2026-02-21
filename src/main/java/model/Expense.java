package model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class Expense {

    private int id;
    private LocalDate date;
    private String description;
    private double amount;

    public Expense(String description, double amount) {
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
        return String.format("0;%s;%s;%s\n", LocalDate.now(), this.description, this.amount);
    }

    public static  Expense fromCsv(String csvLine) {
        List<String> line = List.of(csvLine.split(";"));
        try {
            int id = Integer.parseInt(line.get(0));
            LocalDate date = LocalDate.parse(line.get(1));
            String description = line.get(2).trim();
            double amount = Double.parseDouble(line.get(3));
            return new Expense(id, date, description, amount);
        } catch (NumberFormatException e) {
            System.out.println("Id ou quantidade inválidos");
            return null;
        } catch (DateTimeException e) {
            System.out.println("Data inválida");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Argumentos não foram passados corretamente!");
            return null;
        }
    }

    @Override
    public String toString() {
        return "#  " + id + "   " + date + "   " + description + "   " +  "R$" + amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
