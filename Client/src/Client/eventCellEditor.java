package Client;

import org.jabricks.basegui.grid.CheckBoxCellEditor;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.net.MalformedURLException;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class eventCellEditor {
    public static void setTableCellEditor(JTable table, JFrame frame) {

        /*table.getModel().addTableModelListener(
            new TableModelListener(){
                @Override
                public void tableChanged(TableModelEvent evt){
                    int rowIndex = table.getEditingRow();
                    System.out.println(rowIndex);
                    if (rowIndex == 2) {
                        JDialog edit_date_frame = new JDialog(frame, "Изменение даты", true);
                        edit_date_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        edit_date_frame.setSize(400, 400);
                        edit_date_frame.setLocationRelativeTo(null);

                        JLabel label = new JLabel("Выберите новую дату");
                        JDatePickerImpl date = EventAddForm.getCalendarInput();
                        label.setLabelFor(date);
                        JPanel panel = new JPanel();
                        panel.add(label);
                        panel.add(date);

                        //кнопка сохранения
                        JButton saveButton = new JButton("Сохранить");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String add_date;
                                if (date.getModel().getValue() == null) {
                                    add_date = "";
                                } else {
                                    add_date = date.getModel().getYear() + "-" + (date.getModel().getMonth() + 1) + "-" + date.getModel().getDay();
                                }
                                /*try {
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
                                }*/
                                /*table.getModel().setValueAt(add_date, rowIndex, 2);
                                edit_date_frame.dispose();
                            }
                        });

                        //кнопка отмены
                        JButton cancelButton = new JButton("Отменить");
                        cancelButton.addActionListener(e -> edit_date_frame.dispose());

                        edit_date_frame.add(panel, BorderLayout.CENTER);
                        JPanel btn_panel = new JPanel();
                        btn_panel.add(saveButton);
                        btn_panel.add(cancelButton);
                        edit_date_frame.add(btn_panel, BorderLayout.SOUTH);
                        edit_date_frame.setVisible(true);
                    }
                }
            });*/

        // Редактор даты с использованием прокручивающегося списка JSpinner
        class DateCellEditor extends AbstractCellEditor implements TableCellEditor
        {
            // Редактор
            private final JDatePickerImpl date;
            // Конструктор
            public DateCellEditor() {
                date = EventAddForm.getCalendarInput();
            }
            // Метод получения компонента для прорисовки
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                // Изменение значения
                return date;
            }
            // Функция текущего значения в редакторе
            public Object getCellEditorValue() {
                return date.getModel().getYear() + "-" + (date.getModel().getMonth() + 1) + "-" + date.getModel().getDay();
            }
        }

        table.setDefaultEditor(String.class, new DateCellEditor());

        DefaultCellEditor types_editor = new DefaultCellEditor(EventAddForm.getEventTypesInput());
        DefaultCellEditor genres_editor = new DefaultCellEditor(EventAddForm.getGenresInput());

        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                table.getColumnModel().getColumn(i).setCellEditor(types_editor);
            } else if (i == 5) {
                table.getColumnModel().getColumn(i).setCellEditor(genres_editor);
            } else if (i != 2) {
                table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
            }
        }
    }
}
