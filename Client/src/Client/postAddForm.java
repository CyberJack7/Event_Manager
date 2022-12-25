package Client;

import Data.Employee;
import Data.Post;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class postAddForm {
    private static JTextField name;
    private static JTextField wage;

    private final String[] titles = {
            "Название* (до 100 симв.)",
            "Заработная плата*"
    };

    //окно добавления нового мероприятия
    public postAddForm(JFrame frame, ArrayList<Post> posts, postTableModel postsTableModel){
        JDialog add_employee_frame = new JDialog(frame, "Новая должность", true);
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
                case "Название* (до 100 симв.)" -> component = name = new JTextField(30);
                case "Заработная плата*" -> component = wage = new JTextField(30);
            }
            title_label.setLabelFor(component);
            panel.add(title_label);
            panel.add(component);
            main_panel.add(panel);
        }

        //кнопка добавления нового мероприятия
        JButton addButton = new JButton("Добавить должность");
        addButton.addActionListener(e -> {
            if (Objects.equals(name.getText(), "") | Objects.equals(wage.getText(), "")) {
                JOptionPane.showMessageDialog(null,
                        "Заполните обязательные поля!\nОбязательные поля отмечены символом *",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            } else if(name.getText().length() > 50 | wage.getText().length() > 50 | !isNumeric(wage.getText())) {
                JOptionPane.showMessageDialog(null,
                        "Введённое(ые) значение(я) не соответствуют требованиям.\nДопустимое" +
                                " количество символов в поле и формат указаны в скобках рядом с ним",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Post post = new Post(name.getText(), Integer.valueOf(wage.getText()));
                    int post_id = ClientService.getInstance().getService().addPostInDB(post);
                    post.setPostId(post_id);
                    posts.add(post);
                } catch (SQLException | MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                postsTableModel.fireTableDataChanged();
                add_employee_frame.dispose();
            }
        });

        //кнопка отмены добавления нового мероприятия
        JButton cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(e -> add_employee_frame.dispose());

        add_employee_frame.add(main_panel, BorderLayout.CENTER);
        JPanel btn_panel = new JPanel();
        btn_panel.add(addButton);
        btn_panel.add(cancelButton);
        add_employee_frame.add(btn_panel, BorderLayout.SOUTH);
        add_employee_frame.setVisible(true);
    }
    public static boolean isNumeric(String string){
        try {
            int intValue = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
