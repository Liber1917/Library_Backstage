package cn.karlxing.JavaBean;

public class BorrowPO {
    private int id;
    private int studentID;
    private int bookID;
    private int borrowDate;
    private int returnDate;

    // 无参数构造函数
    public BorrowPO() {
    }

    // 带所有参数的构造函数（如果需要）
    public BorrowPO(int studentID, int bookID, int borrowDate, int returnDate) {
        this.studentID = studentID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(int borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BorrowPO{" +
                "id=" + id +
                ", studentID=" + studentID +
                ", bookID=" + bookID +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}