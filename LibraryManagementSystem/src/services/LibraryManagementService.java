package services;

import models.*;

import java.time.LocalDate;
import java.util.*;

public class LibraryManagementService {
    private String id;
    private List<User> users;
    private List<Book> allBooks;
    private List<Rack> racks;
    private Map<String, Integer> bookCopyToRackNoMapping;
    private static final int MAX_NO_OF_BOOKS_USER_CAN_BORROW = 5;
    private LibraryManagementService(String id, int noOfRacks) {
        this.id = id;
        this.users = new ArrayList<>();
        this.allBooks = new ArrayList<>();
        this.racks = new ArrayList<>();
        for(int i=0;i<noOfRacks;i++){
            this.racks.add(new Rack(i));
        }
        bookCopyToRackNoMapping = new HashMap<>();
    }
    public static LibraryManagementService createService(String id, int noOfRacks){
        return new LibraryManagementService(id, noOfRacks);
    }

    public void addBook(String bookId, String title, List<String> authors, List<String> publishers, List<String> bookCopyIds){
        Book book = new Book(bookId, title, authors, publishers);
        allBooks.add(book);
        for(String id: bookCopyIds){
            BookCopy bookCopy = new BookCopy(id, book);
            for(Rack rack: racks){
                if(!rack.getBooksInThisRack().contains(bookCopy)){
                    rack.getBooksInThisRack().add(bookCopy);
                    bookCopy.setRackNo(rack.getRackNo());
                    bookCopyToRackNoMapping.put(bookCopy.getId(), rack.getRackNo());
                    System.out.println(id+" is added to rack no "+rack.getRackNo());
                }
            }
        }
    }

    public List<Book> search(SearchAttribute attribute){
        List<Book> ans = new ArrayList<>();
        switch (attribute.getId().toLowerCase()){
            case "book_id":
                for (Book book: allBooks) {
                    if (book.getId().equalsIgnoreCase(attribute.getValue())) {
                        ans.add(book);
                    }
                }
                break;
            case "author":
                for (Book book: allBooks) {
                    if (book.getAuthors().contains(attribute.getValue())) {
                        ans.add(book);
                    }
                }
                break;
            default:
                System.out.println("No such attribute");
        }
        return ans;
    }

    public void removeBookCopy(String bookCopyId){
        Rack rack = racks.get(bookCopyToRackNoMapping.get(bookCopyId));
        for(BookCopy bookCopy: rack.getBooksInThisRack()){
            if(bookCopy.getId().equalsIgnoreCase(bookCopyId)){
                rack.getBooksInThisRack().remove(bookCopy);
                break;
            }
        }
    }

    public int borrow_book(String bookId, String user, LocalDate dueDate){
        boolean found = false;
        String borrow_book_copy_id = null;
        for(Rack rack: racks){
            if(found){
                break;
            }
            for(BookCopy bookCopy: rack.getBooksInThisRack()){
                if((bookCopy.getBook().getId().equalsIgnoreCase(bookId)) && (bookCopy.getBorrowedBy() == null)){
                    borrow_book_copy_id = bookCopy.getId();
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No book available currently!");
            return -1;
        }

        return borrow_book_copy(borrow_book_copy_id, user, dueDate);
    }

    public int borrow_book_copy(String bookCopyId, String userId, LocalDate dueDate){
        Rack rack = racks.get(bookCopyToRackNoMapping.get(bookCopyId));
        User borrowedBy = null;
        for (User user: users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                borrowedBy = user;
                break;
            }
        }

        if(borrowedBy.getBooksBorrowed().size() == MAX_NO_OF_BOOKS_USER_CAN_BORROW){
            System.out.println("Maximum borrow limit reached for user!");
            return -1;
        }

        for(BookCopy bookCopy: rack.getBooksInThisRack()){
            if(bookCopy.getId().equalsIgnoreCase(bookCopyId)){
                bookCopy.setBorrowedBy(borrowedBy);
                bookCopy.setDueDate(dueDate);
                assert borrowedBy != null;
                borrowedBy.getBooksBorrowed().add(bookCopy);
                break;
            }
        }

        return rack.getRackNo();
    }

    public void return_borrow_book(String bookCopyId){
        Rack rack = racks.get(bookCopyToRackNoMapping.get(bookCopyId));
        for(BookCopy bookCopy: rack.getBooksInThisRack()){
            if(bookCopy.getId().equalsIgnoreCase(bookCopyId)){
                User borrowedBy = bookCopy.getBorrowedBy();
                bookCopy.setBorrowedBy(null);
                bookCopy.setDueDate(null);
                borrowedBy.getBooksBorrowed().remove(bookCopy);
                break;
            }
        }
    }

    public void print_borrowed(String userId){
        for (User user: users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                for (BookCopy bookCopy: user.getBooksBorrowed()) {
                    System.out.println(bookCopy.toString());
                }
                break;
            }
        }
    }
}
