package cn.karlxing.JavaBean;

public class StudentPO {
    private int id;
    private String name;
    private String dept;
    private String major;
    private int borrowed;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public String toString() {
        return id + " " + name + " " + dept + " " + major + " " + borrowed;
    }

}
