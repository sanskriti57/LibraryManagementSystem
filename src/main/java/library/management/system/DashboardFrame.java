package library.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class DashboardFrame extends JFrame {
    private User currentUser;
    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;

    public DashboardFrame(User user) {
        this.currentUser = user;

        try {
            bookDAO = new BookDAO();
            transactionDAO = new TransactionDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database.");
        }

        setTitle("Library Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 102, 204)); // Blue
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 250, 255)); // Match background
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        String[] buttons = {"Add Book", "List Books", "Check Out", "Check In", "Transactions"};

        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);

            // 🔵 Blue button
            btn.setBackground(new Color(30, 144, 255)); // DodgerBlue
            btn.setForeground(Color.WHITE);

            // 🔠 Bigger font
            btn.setFont(new Font("Arial", Font.BOLD, 18));

            // 🧍 Add padding
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setFocusable(false);

            btn.addActionListener(e -> handleAction(btnText));
            buttonPanel.add(Box.createVerticalStrut(15)); // space between buttons
            buttonPanel.add(btn);
        }

        add(titlePanel, BorderLayout.NORTH);     // 🔵 title on top
        add(buttonPanel, BorderLayout.CENTER);   // buttons in middle

        setVisible(true);
        buttonPanel.setBackground(new Color(245, 250, 255)); // light blue background
        getContentPane().setBackground(new Color(245, 250, 255));

    }

    private void handleAction(String action) {
        switch (action) {
            case "Add Book":
                new AddBookDialog(this, bookDAO);
                break;
            case "List Books":
                showBooks();
                break;
            case "Check Out":
                new CheckoutDialog(this, bookDAO, transactionDAO, currentUser);
                break;
            case "Check In":
                new CheckinDialog(this, bookDAO, transactionDAO);
                break;
            case "Transactions":
                showTransactions();
                break;
        }
    }

    private void showBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            StringBuilder sb = new StringBuilder();
            for (Book b : books) {
                sb.append(b.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Book List", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch book list.");
        }
    }

    private void showTransactions() {
        try {
            List<Transaction> list = transactionDAO.getAllTransactions();
            StringBuilder sb = new StringBuilder();
            sb.append("ID\tUserID\tBookID\tIssue\tDue\tReturn\n");
            sb.append("--------------------------------------------------\n");
            for (Transaction t : list) {
                sb.append(t.getId()).append("\t")
                  .append(t.getUserId()).append("\t")
                  .append(t.getBookId()).append("\t")
                  .append(t.getIssueDate()).append("\t")
                  .append(t.getDueDate()).append("\t")
                  .append(t.getReturnDate() != null ? t.getReturnDate() : "N/A")
                  .append("\n");
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Courier New", Font.PLAIN, 14));
            area.setForeground(Color.DARK_GRAY);
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new Dimension(600, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "All Transactions", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }


