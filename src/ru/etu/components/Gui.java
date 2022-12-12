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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;

import java.sql.Statement;
import java.sql.ResultSet;

public class Gui {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Мероприятия");
        //frame.setTitle("Новое окно!!!");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        table(frame);

//        initContent(frame);
        frame.setVisible(true);
    }

    private static void table(JFrame frame) throws SQLException {

        //создание и заполнение таблицы с мероприятиями
        ArrayList<Integer> arEventsId = Queries.getEventsId();
        ArrayList<Event> events = new ArrayList<>();
        for (int event_id : arEventsId) {
            events.add(new Event(event_id));
        }
        eventTableModel eventTableModel = new eventTableModel(events);
        JTable table = new JTable(eventTableModel);
        frame.add(new JScrollPane(table));

        //кнопка открытия окна добавления нового мероприятия
        JButton addButton = new JButton("Создать мероприятие");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //окно добавления нового мероприятия
                JFrame frame_add_event = new JFrame("Новое мероприятие");
                frame_add_event.setSize(800, 600);
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
                //GridLayout layout = new GridLayout(8, 1);
                main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
                String[] titles = {
                        "Название мероприятия",
                        "Тематика",
                        "Дата и время",
                        "Место проведения",
                        "Тип мероприятия",
                        "Жанр",
                        "Описание",
                        "Программа"
                };

                String[] genres;
                try {
                    genres = Queries.getGenresInfo();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                String[] event_types;
                try {
                    event_types = Queries.getEventTypesInfo();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }



                for (String title : titles) {
                    JPanel panel = new JPanel();
                    JLabel title_label = new JLabel(title, JLabel.TRAILING);
                    title_label.setPreferredSize(new Dimension(200, 50));
                    title_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
                    Component input;
                    if (title.equals("Тип мероприятия")) {
                        input = new JComboBox(event_types);
                        input.setPreferredSize(new Dimension(332, 20));
                        title_label.setLabelFor(input);
                    } else if (title.equals("Жанр")) {
                        input = new JComboBox(genres);
                        input.setPreferredSize(new Dimension(332, 20));
                        title_label.setLabelFor(input);
                    } else if (title.equals("Описание") || title.equals("Программа")) {
                        JTextArea text_area = new JTextArea(5, 30);
                        text_area.setLineWrap(true);
                        text_area.setWrapStyleWord(true);
                        input = new JScrollPane(text_area);
                        ((JScrollPane) input).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        input.setPreferredSize(new Dimension(332, 87));
                        title_label.setLabelFor(input);
                    } else if (title.equals("Дата и время")) {
                        input = new JTextField(30);
                        title_label.setLabelFor(input);
                    } else {
                        input = new JTextField(30);
                        title_label.setLabelFor(input);
                    }
                    panel.add(title_label);
                    panel.add(input);
                    main_panel.add(panel);
                }


                //кнопка добавления нового мероприятия
                JButton addButton = new JButton("Добавить мероприятие");
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        events.add(new Event("", "", "", "", 0, 0, "", ""));
                        eventTableModel.fireTableDataChanged();
                        frame_add_event.dispose();
                        frame.setVisible(true);
                    }
                });

                //кнопка отмены добавления нового мероприятия
                JButton cancelButton = new JButton("Отменить");
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
        });

        //кнопка удаления мероприятий
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
                if (selectedIndices.length != 0) {
                    ArrayList<Event> deleted_events = new ArrayList<>();
                    for(int i: selectedIndices){
                        deleted_events.add(events.get(i));
                    }
                    String title = "";
                    if (selectedIndices.length == 1) {
                        title = "Вы уверены, что хотите удалить мероприятие " + table.getValueAt(selectedIndices[0], 0) + "?";
                    } else {
                        title = "Вы уверены, что хотите удалить мероприятий: " + selectedIndices.length + " шт?";
                    }
                    UIManager.put("OptionPane.yesButtonText"   , "Да"    );
                    UIManager.put("OptionPane.noButtonText"    , "Нет"   );
                    int confirmation = JOptionPane.showConfirmDialog(
                            null,
                            title,
                            "Подтверждение удаления",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    System.out.println(confirmation);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        events.removeAll(deleted_events);
                        eventTableModel.fireTableDataChanged();
                    }
                }

            }
        });

        JPanel btn_panel = new JPanel();
        btn_panel.add(addButton);
        btn_panel.add(delButton);
        frame.add(btn_panel, BorderLayout.SOUTH);
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
