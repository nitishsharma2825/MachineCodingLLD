package v1.services;

import v1.models.Expense;
import v1.models.User;

import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private List<Expense> allExpenses;

    public ExpenseService() {
        this.allExpenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        allExpenses.add(expense);
        for(User user: expense.getUsersInvolved()){
            if(user.equals(expense.getOwnerUser())){
                continue;
            }
            user.updateBalance(expense.getOwnerUser(), expense.getAmount());
        }
    }

    public void displayAllExpenses(){
        for(Expense expense: allExpenses){
            System.out.println(expense.getAmount() + " paid by "+" "+expense.getOwnerUser().getName() +" to "+ expense.getUsersInvolved().toString());
        }
    }
}
