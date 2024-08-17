import models.Book;
import models.SearchAttribute;
import services.LibraryManagementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        LibraryManagementService libraryManagementService = null;
        boolean exit = false;
        while (true) {
            String[] commands = sc.nextLine().split(" ");
            switch (commands[0].toLowerCase()) {
                case "create_library":
                    libraryManagementService = LibraryManagementService.createService(commands[1], Integer.parseInt(commands[2]));
                    break;
                case "add_book":
                    List<String> authors = new ArrayList<>();
                    List<String> publishers = new ArrayList<>();
                    List<String> bookCopyIds = new ArrayList<>();
                    Collections.addAll(authors, commands[3].split(","));
                    Collections.addAll(publishers, commands[4].split(","));
                    Collections.addAll(bookCopyIds, commands[5].split(","));
                    assert libraryManagementService != null;
                    libraryManagementService.addBook(
                            commands[1],
                            commands[2],
                            authors,
                            publishers,
                            bookCopyIds
                    );
                    break;
                case "search":
                    SearchAttribute attribute = new SearchAttribute(commands[2], commands[3]);
                    assert libraryManagementService != null;
                    List<Book> books = libraryManagementService.search(attribute);
                    for(Book book: books){
                        System.out.println(book.toString());
                    }
                    break;
                case "remove_book_copy":
                    assert libraryManagementService != null;
                    libraryManagementService.removeBookCopy(commands[2]);
                    break;
                case "borrow_book":
                    assert libraryManagementService !=null;
                    int rackNo = libraryManagementService.borrow_book(commands[2], commands[3], LocalDate.parse(commands[4]));
                    System.out.println("Borrowed from "+" "+rackNo);
                    break;
                case "return_book_copy":
                    assert libraryManagementService != null;
                    libraryManagementService.return_borrow_book(commands[2]);
                    break;
                case "print_borrowed":
                    assert libraryManagementService != null;
                    libraryManagementService.print_borrowed(commands[2]);
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("No such operation exists!");
                    exit = true;
            }
            if (exit) {
                return;
            }
        }
    }
}
