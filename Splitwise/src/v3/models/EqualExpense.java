package v3.models;

import java.util.List;

public class EqualExpense extends Expense{

    public EqualExpense(double amount, ExpenseMetadata expenseMetadata, List<Split> splits, User paidBy) {
        super(amount, expenseMetadata, splits, paidBy);
    }

    @Override
    public boolean validate() {
        for (Split split: getSplits()){
            if (!(split instanceof EqualSplit)) {
                return false;
            }
        }

        return true;
    }
}
