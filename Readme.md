# Expense Tracker CLI

Um gerenciador de despesas simples desenvolvido em **Java**, que funciona via linha de comando e armazena dados em arquivos CSV.

---

## Funcionalidades

* **Adicionar:** Registra despesa com descrição e valor.
* **Listar:** Exibe todas as despesas salvas.
* **Excluir:** Remove uma despesa através do ID.
* **Resumo:** Exibe o gasto total geral ou filtrado por mês.

---

## Como Usar

Execute o programa passando os argumentos no terminal:

```bash
# Adicionar
java ExpenseTrackerCli add --description "Almoço" --amount 20

# Listar
java ExpenseTrackerCli list

# Resumo Total
java ExpenseTrackerCli summary

# Resumo por Mês (Ex: Agosto)
java ExpenseTrackerCli summary --month 8

# Deletar
java ExpenseTrackerCli delete --id 1
```

URL ROADMAP: https://roadmap.sh/projects/expense-tracker