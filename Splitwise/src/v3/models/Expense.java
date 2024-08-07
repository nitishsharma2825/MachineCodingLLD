package v3.models;

import java.util.List;

public abstract class Expense {
    private String id;
    private double amount;
    private ExpenseMetadata expenseMetadata;
    private List<Split> splits;
    private User paidBy;

    public Expense(double amount, ExpenseMetadata expenseMetadata, List<Split> splits, User paidBy) {
        this.amount = amount;
        this.expenseMetadata = expenseMetadata;
        this.splits = splits;
        this.paidBy = paidBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ExpenseMetadata getExpenseMetadata() {
        return expenseMetadata;
    }

    public void setExpenseMetadata(ExpenseMetadata expenseMetadata) {
        this.expenseMetadata = expenseMetadata;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public abstract boolean validate();
}
