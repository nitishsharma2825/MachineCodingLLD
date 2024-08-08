package v1.models;

import java.util.Objects;
import java.util.UUID;

public class Card {
    private String id;
    private User assignedUser;
    private String name;
    private String description;

    public Card(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void display(){
        System.out.println("Id: "+" "+id+"Name: "+name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
