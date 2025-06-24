package library.management.system;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
public class AddBookDialog extends JDialog {
    public AddBookDialog(JFrame parent, BookDAO dao) {
        super(parent, "Add Book", true);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Add New Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        form.setBackground(new Color(245, 250, 255));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Author:"));
        form.add(authorField);

        JButton submit = new JButton("Add Book");
        styleButton(submit);

        form.add(new JLabel()); // filler
        form.add(submit);

        submit.addActionListener(e -> {
            try {
                dao.addBook(new Book(0, titleField.getText(), authorField.getText()));
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        add(titleLabel, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
