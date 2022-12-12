package ru.etu.test;

public class Student extends User{

    private String name;
    private String number;
    private byte course;

    public Student(String name, String number) {
        this.name = name;
        this.number = number;
        this.course=1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public boolean setNumber(String number) {
        if (number.length() == 6) {
            this.number = number;
            return true;
        } else {
            return false;
        }
    }

    public byte getCourse() {
        return course;
    }

    public void setCourse(byte course) {
        this.course = course;
    }

    public void showInfo() {

    }
}
