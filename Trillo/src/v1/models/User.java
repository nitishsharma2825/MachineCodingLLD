package v1.models;

import v1.services.RandomIDGenerator;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;

    public User(String name) {
        this.id = RandomIDGenerator.generateRandomString(7);
        this.name = name;
    }
    public User() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void display(){
        System.out.println("Id: "+" "+id+"Name: "+name);
    }
}
