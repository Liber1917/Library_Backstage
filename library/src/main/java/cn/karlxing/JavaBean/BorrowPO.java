package cn.karlxing.JavaBean;

public class BorrowPO {
    private int id; // log自身的ID
    private int studentID;
    private int bookID;
    private int borrowDate; // 借书日期，使用整数存储（yyMMdd格式）
    private int returnDate; // 还书日期，使用整数存储（yyMMdd格式）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Book ID: " + bookID +
                ", Borrow Date: " + (borrowDate != 0 ? String.format("%06d", borrowDate) : "未还") + ", Return Date: " + (returnDate != 0 ? String.format("%06d", returnDate) : "未还");
    }
}