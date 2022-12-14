package ru.etu.components;

import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.model.UtilDateModel;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

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
    public EventAddForm(JFrame frame, ArrayList<Event> events, eventTableModel eventTableModel){
        JFrame frame_add_event = new JFrame("Новое мероприятие");
        frame_add_event.setSize(600, 600);
        frame_add_event.setLocationRelativeTo(null);
        frame_add_event.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            frame_add_event.dispose();
            frame.setVisible(true);
            }
            /*@Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                JOptionPane.showMessageDialog(null, "Welcome to the System");
            }*/
        });
        frame.dispose();

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
                        int event_id = Event.addEventInDB(event);
                        System.out.println(event_id);
                        event.setEventId(event_id);
                        events.add(event);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    eventTableModel.fireTableDataChanged();
                    frame_add_event.dispose();
                    frame.setVisible(true);
                }
            }
        });

        //кнопка отмены добавления нового мероприятия
        cancelButton = new JButton("Отменить");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_add_event.dispose();
                frame.setVisible(true);
            }
        });
        frame_add_event.add(main_panel, BorderLayout.CENTER);
        JPanel btn_panel = new JPanel();
        btn_panel.add(addButton);
        btn_panel.add(cancelButton);
        frame_add_event.add(btn_panel, BorderLayout.SOUTH);
        frame_add_event.setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
    }

    private JComboBox getGenres(){
        String[] genres; //список доступных жанров
        try {
            genres = Queries.getGenresInfo();
        } catch (
                SQLException ex) {
            throw new RuntimeException(ex);
        }
        JComboBox genres_input = new JComboBox(genres);
        genres_input.setPreferredSize(new Dimension(332, 20));
        return genres_input;
    }

    private JComboBox getEventTypes(){
        String[] event_types; //список доступных типов мероприятий
        try {
            event_types = Queries.getEventTypesInfo();
        } catch (SQLException ex) {
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
        /*JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(332, 87));
        return scrollPane;*/
    }

    private JScrollPane getScrollPane(JTextArea textArea){
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(332, 87));
        return scrollPane;
    }
}
