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
                fileManagement.list();
                break;
            case "summary":
                System.out.println("Mostrando valor total das despesas");
                String month = getValue(args, "--month");
                if(args.length > 1 && month != null) {
                    fileManagement.getMonthSummary(Integer.parseInt(month));
                } else {
                    fileManagement.getGeneralSummary();
                }
                break;
            case "delete":
                System.out.println("Deletando uma despesa");
                break;
        }
    }

    private boolean checkArgs(String option, String[] args) {
        switch (option) {
            case "add":
                return isQuantityValid(args, 5) && areValueValid(args, "--description") && areValueValid(args, "--amount");
            case "list":
                return isQuantityValid(args, 1);
            case "summary":
                if(args.length > 1) {
                    return isQuantityValid(args, 3) && areValueValid(args,"--month");
                }
                return isQuantityValid(args, 1);
            case "delete":
                System.out.println("Logica para delete");
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

    private boolean areValueValid(String[] args, String flag) {
        String value = getValue(args, flag);

        if (value == null || value.isEmpty()) {
            System.out.printf("Erro: %s ausente ou sem valor.%n", flag);
            return false;
        }

        return switch (flag) {
            case "--month" -> isMonthValid(value);
            case "--amount" -> isInteger(value);
            case "--description" -> true;
            default -> {
                System.out.println("flag invalida");
                yield false;
            }
        };
    }

    private boolean isMonthValid(String value) {
        if(isInteger(value)) {
            boolean isAValidMonth = (Integer.parseInt(value) >= 1 && Integer.parseInt(value) <= 12);

            if(!isAValidMonth) {
                System.out.println("Valor Inválido para mês. Digite um numero entre 1 e 12");
                return false;
            }

            return true;
        }
        return false;
    }

    private boolean isInteger(String parseableValue) {
        try {
            Integer.parseInt(parseableValue);
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
