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

}
