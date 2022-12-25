package Client;

import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class dateCellEditor {
    static class getDateCellEditor extends AbstractCellEditor implements TableCellEditor
    {
        // Редактор
        private final JDatePickerImpl date;
        // Конструктор
        public getDateCellEditor() {
            date = calendarPanel.getCalendar();
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
}
