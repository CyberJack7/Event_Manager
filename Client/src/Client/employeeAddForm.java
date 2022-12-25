package Client;

import Data.Employee;
import Data.Event;
import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.model.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class employeeAddForm {
    private static JTextField name;
    private static JTextField surname;
    private static JTextField patronymic;
    private static JTextField email;
    private static JTextField phone;
    private static JDatePickerImpl dateBirth;
    private static JDatePickerImpl employmentDate;
    private static JComboBox<String> post;
    private static JButton addButton;
    private static JButton cancelButton;

    private final String[] titles = {
            "Фамилия* (до 50 симв.)",
            "Имя* (до 50 симв.)",
            "Отчество (до 50 симв.)",
            "Электронная почта* (до 256 симв.)",
            "Номер телефона* (12 симв. начиная с +)",
            "Дата рождения*",
            "Дата приёма на работу*",
            "Должность*"
    };

    //окно добавления нового мероприятия
    public employeeAddForm(JFrame frame, ArrayList<Employee> employees, employeeTableModel employeeTableModel){
        JDialog add_employee_frame = new JDialog(frame, "Новый сотрудник", true);
        add_employee_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add_employee_frame.setSize(700, 700);
        add_employee_frame.setLocationRelativeTo(null);

        JPanel main_panel = new JPanel();

        for (String title : this.titles) {
            JPanel panel = new JPanel();
            JLabel title_label = new JLabel(title, JLabel.TRAILING);
            title_label.setPreferredSize(new Dimension(300, 50));
            title_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            Component component = null;
            switch (title) {
                case "Фамилия* (до 50 симв.)" -> component = name = new JTextField(30);
                case "Имя* (до 50 симв.)" -> component = surname = new JTextField(30);
                case "Отчество (до 50 симв.)" -> component = patronymic = new JTextField(30);
                case "Электронная почта* (до 256 симв.)" -> component = email = new JTextField(30);
                case "Номер телефона* (12 симв. начиная с +)" -> component = phone = new JTextField(30);
                case "Дата рождения*" -> component = dateBirth = calendarPanel.getCalendar();
                case "Дата приёма на работу*" -> component = employmentDate = calendarPanel.getCalendar();
                case "Должность*" -> component = post = getPosts();
            }
            title_label.setLabelFor(component);
            panel.add(title_label);
            panel.add(component);
            main_panel.add(panel);
        }

        //кнопка добавления нового мероприятия
        addButton = new JButton("Добавить сотрудника");
        addButton.addActionListener(e -> {
            if (Objects.equals(name.getText(), "") | Objects.equals(surname.getText(), "") | Objects.equals(email.getText(), "") |
                    Objects.equals(phone.getText(), "") | dateBirth.getModel().getValue() == null | employmentDate.getModel().getValue() == null) {
                JOptionPane.showMessageDialog(null,
                        "Заполните обязательные поля!\nОбязательные поля отмечены символом *",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            } else if(name.getText().length() > 50 | surname.getText().length() > 50 | patronymic.getText().length() > 50 |
                    email.getText().length() > 256 | phone.getText().length() != 12 | !Objects.equals(phone.getText().charAt(0), '+')) {
                JOptionPane.showMessageDialog(null,
                        "Введённое(ые) значение(я) не соответствуют требованиям.\nДопустимое" +
                                " количество символов в поле и формат указаны в скобках рядом с ним",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String date_birth = dateBirth.getModel().getYear() + "-" + (dateBirth.getModel().getMonth() + 1) + "-" + dateBirth.getModel().getDay();
                String employment_date = employmentDate.getModel().getYear() + "-" + (employmentDate.getModel().getMonth() + 1) + "-" + employmentDate.getModel().getDay();
                try {
                    Employee employee = new Employee(
                            name.getText(),
                            surname.getText(),
                            patronymic.getText(),
                            email.getText(),
                            phone.getText(),
                            date_birth,
                            employment_date,
                            (String) post.getSelectedItem()
                    );
                    int employee_id = ClientService.getInstance().getService().addEmployeeInDB(employee);
                    employee.setEmployeeId(employee_id);
                    employees.add(employee);
                } catch (SQLException | MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                employeeTableModel.fireTableDataChanged();
                add_employee_frame.dispose();
            }
        });

        //кнопка отмены добавления нового мероприятия
        cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(e -> add_employee_frame.dispose());

        add_employee_frame.add(main_panel, BorderLayout.CENTER);
        JPanel btn_panel = new JPanel();
        btn_panel.add(addButton);
        btn_panel.add(cancelButton);
        add_employee_frame.add(btn_panel, BorderLayout.SOUTH);
        add_employee_frame.setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
    }

    private static JComboBox<String> getPosts(){
        String[] posts; //список доступных жанров
        try {
            posts = ClientService.getInstance().getService().getPosts();
        } catch (
                MalformedURLException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox<String> posts_input = new JComboBox<>(posts);
        posts_input.setPreferredSize(new Dimension(332, 20));
        return posts_input;
    }

    public static JComboBox<String> getPostsInput(){
        return getPosts();
    }
}
