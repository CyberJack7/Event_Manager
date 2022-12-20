package Client;

import Data.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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
        jTabbedPane.addTab("Расписание", table(frame));
        jTabbedPane.addTab("Сотрудники", new JPanel());

        frame.add(jTabbedPane);
        frame.setVisible(true);
    }

    private static JPanel table(JFrame frame) throws SQLException, MalformedURLException {
        JPanel table_panel = new JPanel();
        table_panel.setLayout(new BorderLayout());
        //table_panel.setLayout(new BoxLayout(table_panel, BoxLayout.Y_AXIS));

        //создание и заполнение таблицы с мероприятиями
        ArrayList<Integer> arEventsId = ClientService.getInstance().getService().getEventsId();
        ArrayList<Event> events = new ArrayList<>();
        for (int event_id : arEventsId) {
            events.add(new Event(event_id));
        }
        eventTableModel eventTableModel = new eventTableModel(events);
        JTable table = new JTable(eventTableModel);
        eventCellEditor.setTableCellEditor(table, frame);

        table_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        //кнопка открытия окна добавления нового мероприятия
        JButton addButton = new JButton("Создать мероприятие");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new EventAddForm(frame, events, eventTableModel); }
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
                                Event.deleteEventFromDB(deleted_events);
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

    /*private static void initContent(JFrame frame){
        frame.setLayout(new FlowLayout());
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

        frame.add(list, BorderLayout.WEST);

    }*/
}
