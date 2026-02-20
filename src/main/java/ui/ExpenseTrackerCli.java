package ui;

import persistence.ExpenseTrackerFileManagement;

public class ExpenseTrackerCli {

    ExpenseTrackerFileManagement fileManagement;

    static void main(String[] args) {
        ExpenseTrackerCli expenseCli = new ExpenseTrackerCli();
        expenseCli.doAction(args);
    }

    ExpenseTrackerCli() {
        String fileName = "C:\\expenseTracker\\expense.csv";
        fileManagement = new ExpenseTrackerFileManagement(fileName);
    }

    private void doAction(String[] args) {
        String option = args[0].toLowerCase();
        switch (option) {
            case "add":
                if(checkArgs(option, args)) {
                    System.out.println("Argumentos corretos para adicionar");
                }
                System.out.println("Adicionando uma despesa");
                fileManagement.add();
                break;
            case "list":
                System.out.println("Listando todas as despesas");
                break;
            case "summary":
                System.out.println("Mostrando valor total das despesas");
                break;
            case "delete":
                System.out.println("Deletando uma despesa");
                break;
            default:
                System.out.println("A opção " + option + " é inválida." );
                System.out.println("Por favor ensira um valor válido [add, list, summary, delete]");
        }
    }

    private boolean checkArgs(String option, String[] args) {
        switch (option) {
            case "add":
                return isQuantityValid(args, 5) || areValuesValid(args);
            case "list":
                System.out.println("Logica para list");
                break;
            case "summary":
                System.out.println("Logia para summary");
                break;
            case "delete":
                System.out.println("Logia capara delete");
                break;
            default:
                System.out.println("Opção inválida");
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
