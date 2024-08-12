package models;

import java.util.HashSet;

public class Rack {
    private int rackNo;
    private HashSet<BookCopy> booksInThisRack;

    public Rack(int rackNo) {
        this.rackNo = rackNo;
        this.booksInThisRack = new HashSet<>();
    }

    public int getRackNo() {
        return rackNo;
    }

    public void setRackNo(int rackNo) {
        this.rackNo = rackNo;
    }

    public HashSet<BookCopy> getBooksInThisRack() {
        return booksInThisRack;
    }

    public void setBooksInThisRack(HashSet<BookCopy> booksInThisRack) {
        this.booksInThisRack = booksInThisRack;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
