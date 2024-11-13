package cn.karlxing.JavaBean;

public class BorrowPO {
    private int studentID;
    private int bookID;
    private int borrowDate;
    private int returnDate;

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

    public String toString() {
        return "Student ID: " + studentID + ", Book ID: " + bookID +
                ", Borrow Date: " + borrowDate + ", Return Date: " + returnDate;
    }
}
