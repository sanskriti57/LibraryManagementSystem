package library.management.system;
//transactions model
import java.sql.Date;

public class Transaction {
    private int id;
    private int userId;
    private int bookId;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;

    public Transaction(int id, int userId, int bookId, Date issueDate, Date dueDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "id=" + id +
               ", userId=" + userId +
               ", bookId=" + bookId +
               ", issueDate=" + issueDate +
               ", dueDate=" + dueDate +
               ", returnDate=" + returnDate +
               '}';
    // Constructors, Getters, Setters
    }
}
