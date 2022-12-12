package ru.etu.test;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class StudentsTableModel extends AbstractTableModel {
    private ArrayList<Student> students = new ArrayList<>();

    public StudentsTableModel(ArrayList<Student> students){
        this.students = students;
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0: return "Name";
            case 1: return "Number";
            case 2: return "Course";
            case 3: return "Login";
            case 4: return "Password";
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 2:
                return Byte.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex){
            case 0:
                return student.getName();
            case 1:
                return student.getNumber();
            case 2:
                return student.getCourse();
            case 3:
                return student.getLogin();
            case 4:
                return student.getPassword();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex){
            case 0:
                student.setName((String) aValue);
                break;
            case 1:
                student.setNumber((String) aValue);
                break;
            case 2:
                student.setCourse((Byte) aValue);
                break;
            case 3:
                student.setLogin((String) aValue);
                break;
            case 4:
                student.setPassword((String) aValue);
                break;
        }
    }
}
