package ru.etu.components;

import ru.etu.components.JdbcRunner;
import ru.etu.components.Queries;
import ru.etu.components.Event;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import java.sql.Statement;
import java.sql.ResultSet;

public class Gui {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Новое окно");
        frame.setTitle("Новое окно!!!");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


//        initContent(frame);
        table(frame);


        /*JFrame frame = new JFrame("Новое окно");
        frame.setTitle("Новое окно!!!");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton button = new JButton(name);
        frame.add(button, BorderLayout.SOUTH);

        frame.setVisible(true);*/

        frame.setVisible(true);

    }

    private static void table(JFrame frame) throws SQLException {


        ArrayList<Integer> arEventsId = Queries.getEventsId();
        frame.setLayout(new GridLayout(2, 2));
        for (int event_id : arEventsId) {
            Event event = new Event(event_id);
            System.out.println(event);
            JPanel panel = new JPanel();
            JLabel title = new JLabel("Название мероприятия:");
            JLabel value = new JLabel(event.getEventName());
            panel.add(title);
            panel.add(value);
            frame.add(panel);
            JPanel panel1 = new JPanel();
            JLabel title1 = new JLabel("Тематика:");
            JLabel value1 = new JLabel(event.getSubject());
            panel1.add(title1);
            panel1.add(value1);
            frame.add(panel1);
            JPanel panel2 = new JPanel();
            JLabel title2 = new JLabel("Дата и время:");
            JLabel value2 = new JLabel(event.getDate());
            panel2.add(title2);
            panel2.add(value2);
            frame.add(panel2);
            JPanel panel3 = new JPanel();
            JLabel title3 = new JLabel("Место:");
            JLabel value3 = new JLabel(event.getPlace());
            panel3.add(title3);
            panel3.add(value3);
            frame.add(panel3);
            JPanel panel4 = new JPanel();
            JLabel title4 = new JLabel("Тип мероприятия:");
            JLabel value4 = new JLabel(String.valueOf(event.getEventTypeId()));
            panel4.add(title4);
            panel4.add(value4);
            frame.add(panel4);
            JPanel panel5 = new JPanel();
            JLabel title5 = new JLabel("Жанр:");
            JLabel value5 = new JLabel(String.valueOf(event.getGenreId()));
            panel5.add(title5);
            panel5.add(value5);
            frame.add(panel5);
            JPanel panel6 = new JPanel();
            JLabel title6 = new JLabel("Описание:");
            JLabel value6 = new JLabel(event.getDescription());
            panel6.add(title6);
            panel6.add(value6);
            frame.add(panel6);
            JPanel panel7 = new JPanel();
            JLabel title7 = new JLabel("Программа:");
            JLabel value7 = new JLabel(event.getProgram());
            panel7.add(title7);
            panel7.add(value7);
            frame.add(panel7);
        }
        JButton addButton = new JButton("Создать мероприятие");
        JButton delButton = new JButton("Удалить");
        JPanel panel8 = new JPanel();
        panel8.add(delButton);
        panel8.add(addButton);
        frame.add(panel8, BorderLayout.SOUTH);
//        frame.add(new JScrollPane(table));

        /*ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Name1", "1"));
        students.add(new Student("Name2", "2"));
        students.add(new Student("Name3", "3"));
        students.add(new Student("Name4", "4"));

        StudentsTableModel studentsTableModel = new StudentsTableModel(students);
        JTable table = new JTable(studentsTableModel);
        TableCellRenderer renderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ((column+row)%2==0){
                    setForeground(Color.red);
                }else{
                    setForeground(Color.blue);
                }
                return this;
            }
        };
        table.setDefaultRenderer(String.class, renderer);
        table.setDefaultRenderer(Byte.class, renderer);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                students.add(new Student("Name???", "??????"));
                studentsTableModel.fireTableDataChanged();
            }
        });

        JButton delButton = new JButton("Del");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
                ArrayList<Student> st = new ArrayList<>();
                for(int i: selectedIndices){
                    st.add(students.get(i));
                }
                students.removeAll(st);
                studentsTableModel.fireTableDataChanged();
            }
        });

        JPanel panel = new JPanel();
        panel.add(delButton);
        panel.add(addButton);
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(table));*/




    };
    private static void initContent(JFrame frame){
        /*frame.setLayout(new FlowLayout());
        JToggleButton toggleButton = new JToggleButton("toggleButton");
        JRadioButton radioButton = new JRadioButton("radioButton");
        JCheckBox checkBox = new JCheckBox("checkBox");
        frame.add(toggleButton);
        frame.add(radioButton);
        frame.add(checkBox);*/


        /*JLabel north = new JLabel("North");
        JLabel south = new JLabel("South");
        JLabel east = new JLabel("East");
        JLabel west = new JLabel("West");
        JLabel center = new JLabel("Center");*/

        /*north.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        south.setBorder(BorderFactory.createLineBorder(Color.red));
        east.setBorder(BorderFactory.createLineBorder(Color.green));
        west.setBorder(BorderFactory.createLineBorder(Color.yellow));
        center.setBorder(BorderFactory.createLineBorder(Color.magenta));

        frame.setLayout(new BorderLayout());
        frame.add(north, BorderLayout.NORTH);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(east, BorderLayout.EAST);
        frame.add(west, BorderLayout.WEST);
        //frame.add(center);
        frame.add(center, BorderLayout.CENTER);*/

        /*frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(north);
        frame.add(west);
        frame.add(south);
        frame.add(east);
        frame.add(center);*/

        /*frame.setLayout(new GridLayout(2, 3));

        JPanel panel = new JPanel();
        panel.add(toggleButton);
        panel.add(radioButton);
        panel.add(checkBox);

        JTextField textField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextArea textArea = new JTextArea();
        frame.add(textField);
        frame.add(passwordField);
        frame.add(textArea);

        JButton button = new JButton("Button");
        JButton button2 = new JButton("Button2");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = passwordField.getText();
                textField.setText(text);
                //button2.setEnabled(!text.equals("1"));
                if (text.equals("1")){
                    button2.setEnabled(false);
                    button2.setToolTipText("not 1");
                }else{
                    button2.setEnabled(true);
                    button2.setToolTipText(null);
                }
            }
        };

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(toggleButton);
        buttonGroup.add(radioButton);
        buttonGroup.add(checkBox);



        button.addActionListener(listener);
        frame.add(button);
        frame.add(button2);
        frame.add(panel);*/

        /*JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, west, east);
        //splitPane.setDividerLocation(700);
        splitPane.setDividerLocation(.5);
        frame.add(splitPane);

        JTextField textField = new JTextField();
        JButton button = new JButton("Button");
        frame.add(textField, BorderLayout.NORTH);
        frame.add(button, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {
                    double d = Double.parseDouble(text);
                    splitPane.setDividerLocation(d);
                } catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(frame, "Wrong value: "+text, "Error 001", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/


        //frame.add(new JScrollPane(new JTextArea()));

        /*JList list = new JList<>(new String[]{
                "123",
                "123",
                "123",
                "123",
                "123",
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame.add(list, BorderLayout.WEST);*/

    }
}
