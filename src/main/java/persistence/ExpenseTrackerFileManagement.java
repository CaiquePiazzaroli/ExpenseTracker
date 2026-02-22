package persistence;

import model.Expense;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerFileManagement {

    private final String FILE_NAME;

    public ExpenseTrackerFileManagement(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public void add(String description, String amount) {
        if(!haveHeader()) addHeader();
        try {
            Expense exp = new Expense(description, Double.parseDouble(amount));
            BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            buffer.write(exp.toCsv());
            buffer.close();
        }catch (NumberFormatException e ) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean haveHeader() {
        String firstLine = getFirstLine();
        return firstLine != null && firstLine.equals("ID;DATE;DESCRIPTION;AMOUNT");
    }

    private String getFirstLine() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private void addHeader() {
        System.out.println("Tratando arquivo...");
        List<Expense> expenses = getExpenseList();
        clearFile();
        try{
            BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            buffer.write("ID;DATE;DESCRIPTION;AMOUNT\n");
            for(Expense expense: expenses) {
                buffer.write(expense.toCsv());
            }
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFile() {
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME));
            buffer.write("");
            buffer.close();
        } catch (IOException e) {
            System.out.println("Nao foi possivel limpar o arquivo");
        }
    }

    public void listExpenses() {
        if(getExpenseList().isEmpty()) {
            System.out.println("Lista vazia");
        } else {
            System.out.println("   ID  DATE         DESCRIPTION             AMOUNT");
            getExpenseList().forEach(expense -> System.out.println(expense.toString()));
        }
    }

    public List<Expense> getExpenseList() {
        List<Expense> expenseList = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(this.FILE_NAME));
            String line;
            while (true) {
                line = buffer.readLine();
                if(line == null) break;
                if(Expense.fromCsv(line) != null) {
                    expenseList.add(Expense.fromCsv(line));
                }
            }
        } catch (FileNotFoundException e ) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return expenseList;
    }

}
