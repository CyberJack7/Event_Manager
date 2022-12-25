package Client;

import Data.Employee;
import Data.Event;
import Data.Post;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Gui {
    public static void main(String[] args) throws SQLException, MalformedURLException {
        JFrame frame = new JFrame("Мероприятия");
        frame.setSize(1200, 800);
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Расписание", eventTable(frame));
        jTabbedPane.addTab("Штат", staffTable(frame));

        frame.add(jTabbedPane);
        frame.setVisible(true);
    }

    private static JPanel eventTable(JFrame frame) throws SQLException, MalformedURLException {
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());
        //table_panel.setLayout(new BoxLayout(table_panel, BoxLayout.Y_AXIS));

        //создание и заполнение таблицы с мероприятиями
        ArrayList<Integer> arEventsId = ClientService.getInstance().getService().getEventsId();
        ArrayList<Event> events = new ArrayList<>();
        for (int event_id : arEventsId) {
            events.add(new Event(event_id, ClientService.getInstance().getService().getEventById(event_id)));
        }
        eventTableModel eventTableModel = new eventTableModel(events);
        JTable table = new JTable(eventTableModel);
        eventCell.setTableCellRenderer(table);
        eventCell.setTableCellEditor(table);

        table_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        //кнопка открытия окна добавления нового мероприятия
        JButton addButton = new JButton("Создать мероприятие");
        addButton.addActionListener(e -> new eventAddForm(frame, events, eventTableModel));

        //кнопка удаления мероприятий
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(e -> {
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length != 0) {
                ArrayList<Event> deleted_events = new ArrayList<>();
                for(int i: selectedIndices){
                    deleted_events.add(events.get(i));
                }
                String title;
                if (selectedIndices.length == 1) {
                    title = "Вы уверены, что хотите удалить мероприятие " + table.getValueAt(selectedIndices[0], 0) + "?";
                } else {
                    title = "Вы уверены, что хотите удалить мероприятий: " + selectedIndices.length + " шт?";
                }
                UIManager.put("OptionPane.yesButtonText", "Удалить");
                UIManager.put("OptionPane.noButtonText", "Отменить");
                int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    title,
                    "Подтверждение удаления",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        try {
                            ClientService.getInstance().getService().deleteEventFromDB(deleted_events);
                            events.removeAll(deleted_events);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    eventTableModel.fireTableDataChanged();
                }
            }

        });

        JPanel btn_panel = new JPanel();
        addButton.setPreferredSize(new Dimension(200, 40));
        delButton.setPreferredSize(new Dimension(200, 40));
        btn_panel.add(addButton);
        btn_panel.add(delButton);
        table_panel.add(btn_panel, BorderLayout.SOUTH);
        return table_panel;
    }


    private static JPanel staffTable(JFrame frame) throws SQLException, MalformedURLException {
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane(SwingConstants.LEFT);
        jTabbedPane.addTab("Сотрудники", employeeTable(frame));
        jTabbedPane.addTab("ФОТ", postTable(frame));
        table_panel.add(jTabbedPane);
        return table_panel;
    }

    private static JPanel employeeTable(JFrame frame) throws MalformedURLException, SQLException {
        JPanel employee_panel = new JPanel();
        employee_panel.setLayout(new BorderLayout());
        //создание и заполнение таблицы с сотрудниками
        ArrayList<Integer> arEmployeeId = ClientService.getInstance().getService().getStaffId();
        ArrayList<Employee> employees = new ArrayList<>();
        for (int employee_id : arEmployeeId) {
            employees.add(new Employee(employee_id, ClientService.getInstance().getService().getEmployeeById(employee_id)));
        }

        employeeTableModel employeesTableModel = new employeeTableModel(employees);
        JTable table = new JTable(employeesTableModel);
        employeeCell.setTableCellRenderer(table);
        employeeCell.setTableCellEditor(table);

        employee_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        //кнопка открытия окна добавления нового сотрудника
        JButton addButton = new JButton("Добавить сотрудника");
        addButton.addActionListener(e -> new employeeAddForm(frame, employees, employeesTableModel));

        //кнопка удаления сотрудника
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(e -> {
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length != 0) {
                ArrayList<Employee> deleted_employees = new ArrayList<>();
                for(int i: selectedIndices){
                    deleted_employees.add(employees.get(i));
                }
                String title;
                if (selectedIndices.length == 1) {
                    title = "Вы уверены, что хотите удалить сотрудника " + table.getValueAt(selectedIndices[0], 0) + " " + table.getValueAt(selectedIndices[0], 1) + " " + table.getValueAt(selectedIndices[0], 2) + "?";
                } else {
                    title = "Вы уверены, что хотите удалить сотрудников: " + selectedIndices.length + "?";
                }
                UIManager.put("OptionPane.yesButtonText", "Удалить");
                UIManager.put("OptionPane.noButtonText", "Отменить");
                int confirmation = JOptionPane.showConfirmDialog(
                        null,
                        title,
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        try {
                            ClientService.getInstance().getService().deleteEmployeesFromDB(deleted_employees);
                            employees.removeAll(deleted_employees);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    employeesTableModel.fireTableDataChanged();
                }
            }

        });

        JPanel btn_panel = new JPanel();
        addButton.setPreferredSize(new Dimension(200, 40));
        delButton.setPreferredSize(new Dimension(200, 40));
        btn_panel.add(addButton);
        btn_panel.add(delButton);
        employee_panel.add(btn_panel, BorderLayout.SOUTH);
        return employee_panel;
    }
    private static JPanel postTable(JFrame frame) throws MalformedURLException, SQLException {
        JPanel post_panel = new JPanel();
        post_panel.setLayout(new BorderLayout());
        //создание и заполнение таблицы с должностями
        ArrayList<Integer> arPostId = ClientService.getInstance().getService().getPostsId();
        ArrayList<Post> posts = new ArrayList<>();
        for (int post_id : arPostId) {
            posts.add(ClientService.getInstance().getService().getPostById(post_id));
        }

        postTableModel postsTableModel = new postTableModel(posts);
        JTable table = new JTable(postsTableModel);

        post_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        //кнопка открытия окна добавления новой должности
        JButton addButton = new JButton("Добавить должность");
        addButton.addActionListener(e -> new postAddForm(frame, posts, postsTableModel));

        //кнопка удаления должностей
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(e -> {
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length != 0) {
                ArrayList<Post> deleted_posts = new ArrayList<>();
                for(int i: selectedIndices){
                    deleted_posts.add(posts.get(i));
                }
                String title;
                if (selectedIndices.length == 1) {
                    title = "Вы уверены, что хотите удалить должность " + table.getValueAt(selectedIndices[0], 0) + "?";
                } else {
                    title = "Вы уверены, что хотите удалить должности: " + selectedIndices.length + " шт?";
                }
                UIManager.put("OptionPane.yesButtonText", "Удалить");
                UIManager.put("OptionPane.noButtonText", "Отменить");
                int confirmation = JOptionPane.showConfirmDialog(
                        null,
                        title,
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        try {
                            ClientService.getInstance().getService().deletePostsFromDB(deleted_posts);
                            posts.removeAll(deleted_posts);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    postsTableModel.fireTableDataChanged();
                }
            }

        });

        JPanel btn_panel = new JPanel();
        addButton.setPreferredSize(new Dimension(200, 40));
        delButton.setPreferredSize(new Dimension(200, 40));
        btn_panel.add(addButton);
        btn_panel.add(delButton);
        post_panel.add(btn_panel, BorderLayout.SOUTH);
        return post_panel;
    }

}
