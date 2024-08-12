package services;

import models.*;

import java.util.*;

public class LibraryManagementService {
    private String id;
    private List<User> users;
    private List<Book> allBooks;
    private List<Rack> racks;
    private LibraryManagementService(String id, int noOfRacks) {
        this.id = id;
        this.users = new ArrayList<>();
        this.allBooks = new ArrayList<>();
        this.racks = new ArrayList<>();
        for(int i=0;i<noOfRacks;i++){
            this.racks.add(new Rack(i));
        }
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
        for(Rack rack: racks){
            for(BookCopy bookCopy: rack.getBooksInThisRack()){
                if(bookCopy.getId().equalsIgnoreCase(bookCopyId)){
                    rack.getBooksInThisRack().remove(bookCopy);
                    break;
                }
            }
        }
    }

}
