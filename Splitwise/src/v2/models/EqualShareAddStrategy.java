package v2.models;

public class EqualShareAddStrategy implements ExpenseAddStrategy{

    public EqualShareAddStrategy() {
    }

    @Override
    public void addExpense(Expense expense) {
        for(User user: expense.getUsersInvolved()){
            if(user.equals(expense.getOwnerUser())){
                continue;
            }
            user.updateBalance(expense.getOwnerUser(), -expense.getAmount());
            expense.getOwnerUser().updateBalance(user, expense.getAmount());
        }
    }
}
