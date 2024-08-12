package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private List<BookCopy> booksBorrowed;

    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.booksBorrowed = new ArrayList<>();
    }

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

    public List<BookCopy> getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(List<BookCopy> booksBorrowed) {
        this.booksBorrowed = booksBorrowed;
    }
}
