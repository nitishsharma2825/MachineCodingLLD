package v3.models;

import java.util.List;

public class PercentExpense extends Expense{

    public PercentExpense(double amount, ExpenseMetadata expenseMetadata, List<Split> splits, User paidBy) {
        super(amount, expenseMetadata, splits, paidBy);
    }

    @Override
    public boolean validate() {
        double sumPercent = 0;
        double totalPercent = 100;

        for (Split split: getSplits()){
            if (!(split instanceof PercentSplit)) {
                return false;
            }
            sumPercent += ((PercentSplit) split).getPercent();
        }

        return sumPercent == totalPercent;
    }
}
