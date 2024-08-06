package v2.services;

import v2.models.Expense;
import v2.models.User;
import v2.models.ExpenseAddStrategy;

import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private List<Expense> allExpenses;

    private ExpenseAddStrategy expenseAddStrategy;

    public ExpenseService() {
        this.allExpenses = new ArrayList<>();
    }

    public void setExpenseAddStrategy(ExpenseAddStrategy expenseAddStrategy) {
        this.expenseAddStrategy = expenseAddStrategy;
    }

    public void addExpense(Expense expense) {
        allExpenses.add(expense);
        expenseAddStrategy.addExpense(expense);
    }

    public void displayAllExpenses(){
        for(Expense expense: allExpenses){
            System.out.println(expense.getAmount() + " paid by "+" "+expense.getOwnerUser().getName() +" to "+ expense.getUsersInvolved().toString());
        }
    }
}
