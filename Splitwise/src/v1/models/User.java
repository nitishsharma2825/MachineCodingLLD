package v1.models;

import java.util.HashMap;
import java.util.UUID;
public class User {
    private String id;
    private String name;
    private HashMap<String, Double> balances;
    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.balances = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Double> getBalances() {
        return balances;
    }

    public void updateBalance(User user, double amt){
        this.balances.put(user.getId(), this.balances.getOrDefault(user.getId(), (double) 0) + amt);
    }

    @Override
    public boolean equals(Object obj) {
        User otherUser = (User) obj;
        return this.getName().equals(otherUser.getName()) || this.getId().equals(otherUser.getId());
    }
}
