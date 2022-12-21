package Client;

import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class eventCell {
    //Размер столбцов и переносы строк
    public static void setTableCellRenderer(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        // Определение минимального и максимального размеров столбцов
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            switch (i) {
                case 0, 1, 3, 6, 7 -> {
                    columnModel.getColumn(i).setMinWidth(70);
                    columnModel.getColumn(i).setMaxWidth(200);
                }
                case 4 -> {
                    columnModel.getColumn(i).setMinWidth(120);
                    columnModel.getColumn(i).setMaxWidth(120);
                }
                case 2, 5 -> {
                    columnModel.getColumn(i).setMinWidth(85);
                    columnModel.getColumn(i).setMaxWidth(85);
                }
            }
        }
        table.setRowHeight(table.getRowHeight() + 20);
        class centerRender extends JTextArea implements TableCellRenderer {
            public centerRender() {
                setLineWrap(true);
                setWrapStyleWord(true);
                setOpaque(true);
                setAlignmentY(CENTER_ALIGNMENT);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (isSelected) {
                    setForeground(table.getSelectionForeground());
                    setBackground(table.getSelectionBackground());
                } else {
                    setForeground(table.getForeground());
                    setBackground(table.getBackground());
                }
                setFont(table.getFont());
                if (hasFocus) {
                    setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                    if (table.isCellEditable(row, column)) {
                        setForeground(UIManager.getColor("Table.focusCellForeground"));
                        setBackground(UIManager.getColor("Table.focusCellBackground"));
                    }
                } else {
                    setBorder(new EmptyBorder(1, 2, 1, 2));
                }
                setText((value == null) ? "" : value.toString());
                return this;
            }
        }
        table.setDefaultRenderer(String.class, new centerRender());
    }

    //редактирование ячеек с помощью календаря и combobox
    public static void setTableCellEditor(JTable table) {

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
