package v2.models;

import java.util.List;
import java.util.UUID;

public class Expense {
    private String id;
    private User ownerUser;
    private double amount;
    private ExpenseType expenseType;
    private List<User> usersInvolved;
    private List<Double> userShares;

    public Expense(User ownerUser, double amount, ExpenseType expenseType, List<User> usersInvolved, List<Double> userShares) {
        this.id = UUID.randomUUID().toString();
        this.ownerUser = ownerUser;
        this.amount = amount;
        this.expenseType = expenseType;
        this.usersInvolved = usersInvolved;
        this.userShares = userShares;
    }

    public String getId() {
        return id;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public List<Double> getUserShares() {
        return userShares;
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public List<User> getUsersInvolved() {
        return usersInvolved;
    }
}
