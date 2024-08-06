package v2.models;

public class ExactShareAddStrategy implements ExpenseAddStrategy{

    public ExactShareAddStrategy() {
    }

    @Override
    public void addExpense(Expense expense) {
        for(int i=0;i<expense.getUsersInvolved().size();i++){
            User user = expense.getUsersInvolved().get(i);
            if(user.equals(expense.getOwnerUser())){
                continue;
            }
            double curUserShare = expense.getUserShares().get(i);
            user.updateBalance(expense.getOwnerUser(), -curUserShare);
            expense.getOwnerUser().updateBalance(user, curUserShare);
        }
    }
}
