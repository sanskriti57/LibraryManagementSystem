package library.management.system;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO;

    public LoginFrame() throws SQLException {
        userDAO = new UserDAO();
        setTitle("Login - Library Management System");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        // 🔵 Title Header
        JLabel title = new JLabel("Library Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // 🔷 Form Panel
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        form.setBackground(new Color(245, 250, 255));

        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        styleButton(loginBtn);
        styleButton(registerBtn);

        form.add(loginBtn);
        form.add(registerBtn);

        add(form, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(245, 250, 255));

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }

    private void login() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = userDAO.login(username, password);
            if (user != null) {
                dispose();
                new DashboardFrame(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = new User(username, password); // requires correct constructor
            boolean success = userDAO.registerUser(user);

            JOptionPane.showMessageDialog(this, success ? "Registered!" : "Registration failed.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred.");
        }
    }

    }

