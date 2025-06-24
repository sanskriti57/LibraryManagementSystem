package library.management.system;
// handles mysql CRUD operations
import java.sql.*;
import java.util.*;

public class BookDAO {
    private Connection conn;

    public BookDAO() throws SQLException {
        conn = DBConnection.getConnection(); // Ensure DBConnection class exists
    }

    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books(id, title, author, isBorrowed) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isBorrowed());
            stmt.executeUpdate();
        }
    }

    public void updateBook(int id, String title, String author) throws SQLException {
        String sql = "UPDATE books SET title=?, author=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("isBorrowed")
                ));
            }
        }
        return books;
    }

    public List<Book> searchBooks(String query) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String q = "%" + query.toLowerCase() + "%";
            stmt.setString(1, q);
            stmt.setString(2, q);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("isBorrowed")
                ));
            }
        }
        return books;
    }

    public void checkOutBook(int id) throws SQLException {
        String sql = "UPDATE books SET isBorrowed = TRUE WHERE id = ? AND isBorrowed = FALSE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Book checked out!");
            else System.out.println("Book not available.");
        }
    }

    public void checkInBook(int id) throws SQLException {
        String sql = "UPDATE books SET isBorrowed = FALSE WHERE id = ? AND isBorrowed = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Book checked in!");
            else System.out.println("Book was not checked out.");
        }
    }
}
