package Client;

import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.model.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class EventAddForm {

    private static JTextField eventName;
    private static JTextField subject;
    private static JTextField place;

    private static JComboBox eventTypes;
    private static JComboBox genres;

    private static JTextArea description;
    private static JTextArea program;

    private static JDatePickerImpl date;

    private static JButton addButton;
    private static JButton cancelButton;

    private final String[] titles = {
            "Название мероприятия*",
            "Тематика",
            "Дата",
            "Место проведения",
            "Тип мероприятия",
            "Жанр",
            "Описание",
            "Программа"
    };

    //окно добавления нового мероприятия
    public EventAddForm(JFrame frame, ArrayList<Data.Event> events, eventTableModel eventTableModel){
        JDialog add_event_frame = new JDialog(frame, "Новое мероприятие", true);
        add_event_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add_event_frame.setSize(600, 600);
        add_event_frame.setLocationRelativeTo(null);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));

        ArrayList<Component> input = new ArrayList<>();
        for (String title : this.titles) {
            JPanel panel = new JPanel();
            JLabel title_label = new JLabel(title, JLabel.TRAILING);
            title_label.setPreferredSize(new Dimension(150, 50));
            title_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            Component component = null;
            switch (title) {
                case "Название мероприятия*" -> component = eventName = new JTextField(30);
                case "Тематика" -> component = subject = new JTextField(30);
                case "Дата" -> component = date = getCalendar();
                case "Место проведения" -> component = place = new JTextField(30);
                case "Тип мероприятия" -> component = eventTypes = getEventTypes();
                case "Жанр" -> component = genres = getGenres();
                case "Описание" -> component = getScrollPane(description = getTextArea());
                case "Программа" -> component = getScrollPane(program = getTextArea());
            }
            title_label.setLabelFor(component);
            panel.add(title_label);
            panel.add(component);
            main_panel.add(panel);
        }

        //кнопка добавления нового мероприятия
        addButton = new JButton("Добавить мероприятие");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(eventName.getText(), "")) {
                    JOptionPane.showMessageDialog(null,
                        "Нельзя создать мероприятие без названия!",
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
                        Data.Event event = new Data.Event(
                            eventName.getText(),
                            subject.getText(),
                            add_date,
                            place.getText(),
                            (String) eventTypes.getSelectedItem(),
                            (String) genres.getSelectedItem(),
                            description.getText(),
                            program.getText()
                        );
                        int event_id = Data.Event.addEventInDB(event);
                        System.out.println(event_id);
                        event.setEventId(event_id);
                        events.add(event);
                    } catch (SQLException | MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    eventTableModel.fireTableDataChanged();
                    add_event_frame.dispose();
                }
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

    private JComboBox getGenres(){
        String[] genres; //список доступных жанров
        try {
            genres = ClientService.getInstance().getService().getGenres();
        } catch (
                MalformedURLException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox genres_input = new JComboBox(genres);
        genres_input.setPreferredSize(new Dimension(332, 20));
        return genres_input;
    }

    private JComboBox getEventTypes(){
        String[] event_types; //список доступных типов мероприятий
        try {
            event_types = ClientService.getInstance().getService().getEventTypes();
        } catch (MalformedURLException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox event_types_input = new JComboBox(event_types);
        event_types_input.setPreferredSize(new Dimension(332, 20));
        return event_types_input;
    }

    private JDatePickerImpl getCalendar(){
        UtilDateModel uiModel  = null; // Модель даты
        JDatePanelImpl dtpPanel = null; // Панель даты
        DateLabelFormatter dlf = null; // Формат представления даты в компоненте
        Locale locale = new Locale("rus"); //локализация

        // Создание компонента даты
        uiModel = new UtilDateModel();
        dtpPanel = new JDatePanelImpl (uiModel, locale);
        dlf = new DateLabelFormatter("yyyy-MM-dd");
        JDatePickerImpl calendar = new JDatePickerImpl(dtpPanel, dlf);
        calendar.setPreferredSize(new Dimension(332, 20));
        return calendar;
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
}
