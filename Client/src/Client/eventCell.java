package Client;

import javax.swing.*;

import javax.swing.table.TableColumnModel;

public class eventCell {
    //Размер столбцов и переносы строк
    public static void setTableCellRenderer(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        // Определение минимального и максимального размеров столбцов
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            switch (i) {
                case 0, 1, 3, 6, 7 -> columnModel.getColumn(i).setMinWidth(70);
                case 4 -> columnModel.getColumn(i).setMinWidth(120);
                case 2, 5 -> columnModel.getColumn(i).setMinWidth(85);
            }
        }
        table.setRowHeight(table.getRowHeight() + 20);

        table.setDefaultRenderer(String.class, new cellRender.CellRender());
    }

    //редактирование ячеек с помощью календаря и combobox
    public static void setTableCellEditor(JTable table) {

        // Редактор даты
        table.setDefaultEditor(String.class, new dateCellEditor.getDateCellEditor());

        DefaultCellEditor types_editor = new DefaultCellEditor(eventAddForm.getEventTypesInput());
        DefaultCellEditor genres_editor = new DefaultCellEditor(eventAddForm.getGenresInput());

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
