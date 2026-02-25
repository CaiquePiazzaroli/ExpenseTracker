package persistence;

import model.Expense;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.Month;

public class ExpenseTrackerFileManagement {

    private final String FILE_NAME;
    private final String FILE_HEADER = "ID,DATE,DESCRIPTION,AMOUNT\n";

    public ExpenseTrackerFileManagement(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public void add(String description, String amount) {
        try {
            int nextId = getLastId() + 1;
            Expense exp = new Expense(nextId, description, Double.parseDouble(amount));

            try(BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                buffer.write(exp.toCsv());
            }

        } catch (NumberFormatException | IOException e ) {
            System.out.println("Erro ao Salvar: " + e);
        }
    }

    public void list() {
        List<Expense> expenses = getExpenseList();
        if (expenses.isEmpty()) {
            System.out.println("Lista vazia");
        } else {
            System.out.println("   ID  DATE         DESCRIPTION             AMOUNT");
            expenses.forEach(System.out::println);
        }
    }

    public void delete(int id) {
        List<Expense> expenses = getExpenseList();

        boolean found = expenses.removeIf(expense -> expense.getId() == id);

        if (found) {
            rewriteDatabase(expenses);
            System.out.printf("Id %d excluido da base de dados com exito!\n", id);
        } else {
            System.out.printf("O id %d não foi encontrado na base de dados.\n", id);
        }
    }

    public void rewriteDatabase(List<Expense> expensesList) {
        try {
            ensureFileExists();

            try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for(Expense exp: expensesList) {
                    buffer.write(exp.toCsv());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getGeneralSummary() {
        List<Expense> expenses = getExpenseList();
        double summary = 0.0;
        for(Expense exp : expenses) {
            summary += exp.getAmount();
        }
        System.out.println("# Total expenses: " + "R$" + summary);
    }

    public void getMonthSummary(int monthNumber) {
        List<Expense> expenses = getExpenseList();
        double summary = 0.0;
        for(Expense exp : expenses) {
            if(exp.getDate().getMonthValue() == monthNumber) {
                summary += exp.getAmount();
            }
        }
        System.out.printf("# Total expenses for %s: R$%.2f", Month.of(monthNumber), summary);
    }

    private int getLastId() {
        return getExpenseList().stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0);
    }

    public List<Expense> getExpenseList() {
        List<Expense> expenseList = new ArrayList<>();
        try {
            ensureFileExists();

            try (BufferedReader buffer = new BufferedReader(new FileReader(this.FILE_NAME))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    if (line.equalsIgnoreCase(FILE_HEADER.trim())) continue;
                    Expense expense = Expense.fromCsv(line);
                    if (expense != null) {
                        expenseList.add(expense);
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Falha crítica ao carregar despesas: " + e.getMessage());
        }

        return expenseList;
    }

    public void ensureFileExists() throws IOException {
        File file = new File(this.FILE_NAME);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Não foi possível criar o diretório: " + parentDir.getAbsolutePath());
            }
        }

        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(FILE_HEADER);
            }
        }
    }
}
