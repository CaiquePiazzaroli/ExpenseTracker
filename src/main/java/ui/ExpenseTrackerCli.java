package ui;

import persistence.ExpenseTrackerFileManagement;

public class ExpenseTrackerCli {

    private final ExpenseTrackerFileManagement fileManagement;

    static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, insira uma opção [add, list, summary, delete]");
            return;
        }
        ExpenseTrackerCli expenseCli = new ExpenseTrackerCli();
        expenseCli.doAction(args);
    }

    ExpenseTrackerCli() {
        final String filename ="C:\\expenseTracker\\expense.csv";
        fileManagement = new ExpenseTrackerFileManagement(filename);
    }

    private void doAction(String[] args) {
        String option = args[0].toLowerCase();

        if (!checkArgs(option, args)) {
            return;
        }

        switch (option) {
            case "add":
                System.out.println("Adicionando uma despesa...");
                String description = getValue(args, "--description");
                String amount = getValue(args, "--amount");
                fileManagement.add(description, amount);
                break;
            case "list":
                System.out.println("Listando todas as despesas");
                fileManagement.listExpenses();
                break;
            case "summary":
                System.out.println("Mostrando valor total das despesas");
                break;
            case "delete":
                System.out.println("Deletando uma despesa");
                break;
        }
    }

    private boolean checkArgs(String option, String[] args) {
        switch (option) {
            case "add":
                return isQuantityValid(args, 5) && areValuesValid(args);
            case "list":
                return isQuantityValid(args, 1);
            case "summary":
                System.out.println("Logia para summary");
                break;
            case "delete":
                System.out.println("Logia capara delete");
                break;
            default:
                System.out.println("Insira um parametro válido: [add, list, summary, delete]");
                break;
        }
        return false;
    }

    private boolean isQuantityValid(String[] args, int minLength) {
        if (args == null || args.length < minLength) {
            System.out.println("Erro: Faltam argumentos. Mínimo esperado: " + minLength);
            return false;
        }
        return true;
    }

    private boolean areValuesValid(String[] args) {
        String desc = getValue(args, "--description");
        String amountStr = getValue(args, "--amount");

        if (desc == null || desc.isEmpty()) {
            System.out.println("Erro: --description ausente ou sem valor.");
            return false;
        }

        if (amountStr == null) {
            System.out.println("Erro: --amount ausente ou sem valor.");
            return false;
        }

        try {
            Integer.parseInt(amountStr);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Erro: O valor de --amount deve ser um número.");
            return false;
        }
    }

    private String getValue(String[] args, String flag) {
        for (int i = 0; i < args.length; i++) {
            if (flag.equals(args[i]) && (i + 1 < args.length)) {
                return args[i + 1];
            }
        }
        return null;
    }
}
