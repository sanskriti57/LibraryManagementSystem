package library.management.system;
//user data access object
//Register, login, get user
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Use hashing in real apps!
            stmt.setString(3, "librarian");
            stmt.executeUpdate();
            return true;
        }
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Again, plain text for demo only
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        }
        return null;
    }
}

