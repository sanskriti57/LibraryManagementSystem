package library.management.system;
//cli interface
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {
    private BookDAO bookDAO;
    private User currentUser;
    private TransactionDAO transactionDAO;

    public LibraryManager() {
        try {
            bookDAO = new BookDAO();
            transactionDAO = new TransactionDAO();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void login(Scanner scanner) {
        try {
            UserDAO userDAO = new UserDAO();
            while (currentUser == null) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                currentUser = userDAO.login(username, password);
                if (currentUser == null) {
                    System.out.println("Invalid credentials. Try again.");
                }
            }
            System.out.println("Logged in as: " + currentUser.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addBook(Book book) {
        try {
            bookDAO.addBook(book);
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public void updateBook(int id, String title, String author) {
        try {
            bookDAO.updateBook(id, title, author);
            System.out.println("Book updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        try {
            bookDAO.deleteBook(id);
            System.out.println("Book deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    public void listBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            if (books.isEmpty()) {
                System.out.println("No books available.");
            } else {
                books.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error listing books: " + e.getMessage());
        }
    }

    public void searchBooks(String query) {
        try {
            List<Book> results = bookDAO.searchBooks(query);
            if (results.isEmpty()) {
                System.out.println("No books found matching the query.");
            } else {
                results.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error searching books: " + e.getMessage());
        }
    }

    public void checkOutBook(int id) {
        try {
            bookDAO.checkOutBook(id);
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            java.sql.Date due = new java.sql.Date(System.currentTimeMillis() + 7 * 86400000L);
            transactionDAO.logIssue(currentUser.getId(), id, today, due);
        } catch (SQLException e) {
            System.err.println("Check-out error: " + e.getMessage());
        }
    }


    public void checkInBook(int id) {
        try {
            bookDAO.checkInBook(id);
            transactionDAO.logReturn(id);
        } catch (SQLException e) {
            System.err.println("Check-in error: " + e.getMessage());
        }
    }


    // Input utility for Book
    private Book inputBookDetails(Scanner scanner) {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();

        return new Book(id, title, author);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        login(scanner); // << Add this before showing menu

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List All Books");
            System.out.println("5. Search Books");
            System.out.println("6. Check Out Book");
            System.out.println("7. Check In Book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addBook(inputBookDetails(scanner));
                    break;
                case 2:
                    System.out.print("Enter Book ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter New Author: ");
                    String author = scanner.nextLine();
                    updateBook(updateId, title, author);
                    break;
                case 3:
                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = scanner.nextInt();
                    deleteBook(deleteId);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    searchBooks(query);
                    break;
                case 6:
                    System.out.print("Enter Book ID to check out: ");
                    int checkoutId = scanner.nextInt();
                    checkOutBook(checkoutId);
                    break;
                case 7:
                    System.out.print("Enter Book ID to check in: ");
                    int checkinId = scanner.nextInt();
                    checkInBook(checkinId);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
