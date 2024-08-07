package v3.services;

import v3.models.*;

import java.util.List;

public class ExpenseService {
    public static Expense createExpense(
            ExpenseType expenseType,
            double amount,
            User paidBy,
            List<Split> splits,
            ExpenseMetadata expenseMetadata) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(amount, expenseMetadata, splits, paidBy);
            case PERCENT:
                for(Split split: splits){
                    PercentSplit percentSplit = (PercentSplit) split;
                    percentSplit.setAmount((amount * percentSplit.getPercent())/100.0);
                }
                return new PercentExpense(amount, expenseMetadata, splits, paidBy);
            case EQUAL:
                double splitAmt = ((double) Math.round(amount*100/splits.size()))/100.0;
                for(Split split: splits) {
                    split.setAmount(splitAmt);
                }
                splits.get(0).setAmount(splitAmt + (amount - splitAmt*splits.size()));
                return new EqualExpense(amount, expenseMetadata, splits, paidBy);
            default:
                return null;
        }
    }
}
