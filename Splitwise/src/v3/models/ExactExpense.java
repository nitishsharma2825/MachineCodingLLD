package v3.models;

import java.util.List;

public class ExactExpense extends Expense{

    public ExactExpense(double amount, ExpenseMetadata expenseMetadata, List<Split> splits, User paidBy) {
        super(amount, expenseMetadata, splits, paidBy);
    }

    @Override
    public boolean validate() {
        double sumAmt = 0;

        for (Split split: getSplits()){
            if (!(split instanceof ExactSplit)) {
                return false;
            }
            sumAmt += split.getAmount();
        }

        double totalAmt = getAmount();

        return sumAmt == totalAmt;
    }
}
