package v2.services;

import v2.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SplitwiseService {
    private List<User> users;

    public SplitwiseService(List<User> users) {
        this.users = users;
    }

    private User getUserByName(String userName){
        for(User user: users){
            if(user.getName().equals(userName)){
                return user;
            }
        }
        return users.get(0);
    }

    public void startService() {
        Scanner sc = new Scanner(System.in);

        ExpenseService expenseService = new ExpenseService();

        while(true) {
            int inputType =  sc.nextInt();
            if(inputType == 1){
                double expenseAmt = sc.nextDouble();
                String userName = sc.next();
                User paidUser = getUserByName(userName);
                int noOfUsers = sc.nextInt();
                List<User> allUsers = new ArrayList<>();
                for(int i=0;i<noOfUsers;i++){
                    String partUserName = sc.next();
                    if(partUserName.equals(userName)){
                        allUsers.add(paidUser);
                        continue;
                    }
                    User partUser = getUserByName(partUserName);
                    allUsers.add(partUser);
                }
                String expenseType = sc.next();
                List<Double> userShares = new ArrayList<>();
                ExpenseAddStrategy expenseAddStrategy = null;
                if(ExpenseType.valueOf(expenseType) == ExpenseType.Percent){
                    expenseAddStrategy = new PercentShareAddStrategy();
                    for(int i=0;i<noOfUsers;i++){
                        userShares.add(sc.nextDouble());
                    }
                } else if (ExpenseType.valueOf(expenseType) == ExpenseType.Equal){
                    expenseAddStrategy = new EqualShareAddStrategy();
                } else if (ExpenseType.valueOf(expenseType) == ExpenseType.Exact){
                    expenseAddStrategy = new ExactShareAddStrategy();
                    for(int i=0;i<noOfUsers;i++){
                        userShares.add(sc.nextDouble());
                    }
                }
                Expense expense = new Expense(paidUser, expenseAmt, ExpenseType.valueOf(expenseType), allUsers, userShares);
                expenseService.setExpenseAddStrategy(expenseAddStrategy);
                expenseService.addExpense(expense);
            } else if(inputType == 2) {
                expenseService.displayAllExpenses();
            } else if(inputType == 3){
                String userName = sc.next();
                User user = this.getUserByName(userName);
                user.displayBalances();
            }
        }
    }

}
