package Client;

import Data.Event;

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
        jTabbedPane.addTab("Расписание", table(frame));

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
        eventCell.setTableCellRenderer(table);
        eventCell.setTableCellEditor(table);

        table_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        //кнопка открытия окна добавления нового мероприятия
        JButton addButton = new JButton("Создать мероприятие");
        addButton.addActionListener(e -> new EventAddForm(frame, events, eventTableModel));

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

        });

        JPanel btn_panel = new JPanel();
        addButton.setPreferredSize(new Dimension(200, 40));
        delButton.setPreferredSize(new Dimension(200, 40));
        btn_panel.add(addButton);
        btn_panel.add(delButton);
        table_panel.add(btn_panel, BorderLayout.SOUTH);
        return table_panel;
    }
}
