package library.management.system;
//Insert + return books + list logs
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TransactionDAO {
    private Connection conn;

    public TransactionDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public void logIssue(int userId, int bookId, Date issueDate, Date dueDate) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, book_id, issue_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, issueDate);
            stmt.setDate(4, dueDate);
            stmt.executeUpdate();
        }
    }

    public void logReturn(int bookId) throws SQLException {
        String sql = "UPDATE transactions SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("book_id"),
                    rs.getDate("issue_date"),
                    rs.getDate("due_date"),
                    rs.getDate("return_date")
                ));
            }
        }
        return list;
    }
}
