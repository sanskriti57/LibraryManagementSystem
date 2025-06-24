package library.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CheckinDialog extends JDialog {
    public CheckinDialog(JFrame parent, BookDAO bookDAO, TransactionDAO transactionDAO) {
    	        super(parent, "Check In Book", true);
    	        setLayout(new BorderLayout());

    	        JLabel title = new JLabel("Check In Book", SwingConstants.CENTER);
    	        title.setFont(new Font("Arial", Font.BOLD, 20));
    	        title.setForeground(new Color(0, 102, 204));
    	        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    	        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
    	        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    	        form.setBackground(new Color(245, 250, 255));

    	        JTextField bookIdField = new JTextField();

    	        form.add(new JLabel("Book ID:"));
    	        form.add(bookIdField);

    	        JButton checkoutBtn = new JButton("Check In");
    	        styleButton(checkoutBtn);
    	        form.add(new JLabel());
    	        form.add(checkoutBtn);

    	        checkoutBtn.addActionListener(e -> {
    	            try {
    	                int bookId = Integer.parseInt(bookIdField.getText());
    	                bookDAO.checkInBook(bookId);
    	                transactionDAO.logReturn(bookId);
    	                JOptionPane.showMessageDialog(this, "Book checked in!");
    	                dispose();
    	            } catch (Exception ex) {
    	                ex.printStackTrace();
    	            }
    	        });

    	        add(title, BorderLayout.NORTH);
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
