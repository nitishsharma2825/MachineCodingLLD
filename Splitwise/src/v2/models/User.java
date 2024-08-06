package v2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class User {
    private final String id;
    private final String name;
    private HashMap<User, Double> balances;
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

    public HashMap<User, Double> getBalances() {
        return balances;
    }

    public void updateBalance(User user, double amt){
        this.balances.put(user, this.balances.getOrDefault(user, (double) 0) + amt);
    }

    public void displayBalances(){
        for(Map.Entry<User, Double> m: this.balances.entrySet()){
            System.out.println(m.getKey() + "owes" + m.getValue() + "to "+ this.name);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()){
            return false;
        }
        User otherUser = (User) obj;
        return this.getName().equals(otherUser.getName()) || this.getId().equals(otherUser.getId());
    }
}
