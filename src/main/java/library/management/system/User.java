package library.management.system;
// user model
public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    // Constructors
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Getters/Setters (omitted for brevity)
}
