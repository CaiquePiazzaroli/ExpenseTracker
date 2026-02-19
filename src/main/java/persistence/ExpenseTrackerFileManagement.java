package persistence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseTrackerFileManagement {

    private final String FILE_NAME;

    public ExpenseTrackerFileManagement(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public void add() {
        String expense = "1;2024-08-06;Lunch;$20\n";
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            buffer.write(expense);
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
