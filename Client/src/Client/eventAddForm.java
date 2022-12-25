package Client;

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

public class eventAddForm {

    private static JTextField eventName;
    private static JTextField subject;
    private static JTextField place;

    private static JComboBox<String> eventTypes;
    private static JComboBox<String> genres;

    private static JTextArea description;
    private static JTextArea program;

    private static JDatePickerImpl date;

    private static JButton addButton;
    private static JButton cancelButton;

    private final String[] titles = {
            "Название мероприятия* (до 50 симв.)",
            "Тематика (до 50 симв.)",
            "Дата",
            "Место проведения (до 200 симв.)",
            "Тип мероприятия",
            "Жанр",
            "Описание (до 500 симв.)",
            "Программа (до 500 симв.)"
    };

    //окно добавления нового мероприятия
    public eventAddForm(JFrame frame, ArrayList<Data.Event> events, eventTableModel eventTableModel){
        JDialog add_event_frame = new JDialog(frame, "Новое мероприятие", true);
        add_event_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add_event_frame.setSize(700, 700);
        add_event_frame.setLocationRelativeTo(null);

        JPanel main_panel = new JPanel();

        for (String title : this.titles) {
            JPanel panel = new JPanel();
            JLabel title_label = new JLabel(title, JLabel.TRAILING);
            title_label.setPreferredSize(new Dimension(250, 50));
            title_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            Component component = null;
            switch (title) {
                case "Название мероприятия* (до 50 симв.)" -> component = eventName = new JTextField(30);
                case "Тематика (до 50 симв.)" -> component = subject = new JTextField(30);
                case "Дата" -> component = date = calendarPanel.getCalendar();
                case "Место проведения (до 200 симв.)" -> component = place = new JTextField(30);
                case "Тип мероприятия" -> component = eventTypes = getEventTypes();
                case "Жанр" -> component = genres = getGenres();
                case "Описание (до 500 симв.)" -> component = getScrollPane(description = getTextArea());
                case "Программа (до 500 симв.)" -> component = getScrollPane(program = getTextArea());
            }
            title_label.setLabelFor(component);
            panel.add(title_label);
            panel.add(component);
            main_panel.add(panel);
        }

        //кнопка добавления нового мероприятия
        addButton = new JButton("Добавить мероприятие");
        addButton.addActionListener(e -> {
            if (Objects.equals(eventName.getText(), "")) {
                JOptionPane.showMessageDialog(null,
                    "Нельзя создать мероприятие без названия!",
                    "Предупреждение",
                    JOptionPane.ERROR_MESSAGE);
            } else if(eventName.getText().length() > 50 | subject.getText().length() > 50 | place.getText().length() > 200 |
                    description.getText().length() > 500 | program.getText().length() > 500) {
                JOptionPane.showMessageDialog(null,
                        "Введённое(ые) значение(я) не соответствуют требованиям.\nДопустимое" +
                                " количество символов в поле и формат указаны в скобках рядом с ним",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String add_date;
                if (date.getModel().getValue() == null) {
                    add_date = "";
                } else {
                    add_date = date.getModel().getYear() + "-" + (date.getModel().getMonth() + 1) + "-" + date.getModel().getDay();
                }
                try {
                    Event event = new Event(
                        eventName.getText(),
                        subject.getText(),
                        add_date,
                        place.getText(),
                        (String) eventTypes.getSelectedItem(),
                        (String) genres.getSelectedItem(),
                        description.getText(),
                        program.getText()
                    );
                    int event_id = ClientService.getInstance().getService().addEventInDB(event);
                    event.setEventId(event_id);
                    events.add(event);
                } catch (SQLException | MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                eventTableModel.fireTableDataChanged();
                add_event_frame.dispose();
            }
        });

        //кнопка отмены добавления нового мероприятия
        cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(e -> add_event_frame.dispose());

        add_event_frame.add(main_panel, BorderLayout.CENTER);
        JPanel btn_panel = new JPanel();
        btn_panel.add(addButton);
        btn_panel.add(cancelButton);
        add_event_frame.add(btn_panel, BorderLayout.SOUTH);
        add_event_frame.setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
    }

    private static JComboBox<String> getGenres(){
        String[] genres; //список доступных жанров
        try {
            genres = ClientService.getInstance().getService().getGenres();
        } catch (
                MalformedURLException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox<String> genres_input = new JComboBox<>(genres);
        genres_input.setPreferredSize(new Dimension(332, 20));
        return genres_input;
    }

    private static JComboBox<String> getEventTypes(){
        String[] event_types; //список доступных типов мероприятий
        try {
            event_types = ClientService.getInstance().getService().getEventTypes();
        } catch (MalformedURLException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox<String> event_types_input = new JComboBox<>(event_types);
        event_types_input.setPreferredSize(new Dimension(332, 20));
        return event_types_input;
    }

    private JTextArea getTextArea(){
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JScrollPane getScrollPane(JTextArea textArea){
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(332, 87));
        return scrollPane;
    }

    public static JComboBox<String> getEventTypesInput(){
        return getEventTypes();
    }

    public static JComboBox<String> getGenresInput(){
        return getGenres();
    }
}
