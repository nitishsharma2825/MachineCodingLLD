package models;

import java.time.LocalDate;

public class BookCopy {
    private Book book;
    private int rackNo;
    private String id;
    private User borrowedBy;
    // LocalDateTime can be used if time is also needed
    private LocalDate dueDate;

    public BookCopy(String id, Book book) {
        this.book = book;
        this.id = id;
        this.borrowedBy = null;
        this.dueDate = null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRackNo() {
        return rackNo;
    }

    public void setRackNo(int rackNo) {
        this.rackNo = rackNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "book=" + book +
                ", rackNo=" + rackNo +
                ", id='" + id + '\'' +
                ", borrowedBy=" + borrowedBy +
                ", dueDate=" + dueDate +
                '}';
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
