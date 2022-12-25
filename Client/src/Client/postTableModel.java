package Client;

import Data.Post;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class postTableModel  extends AbstractTableModel {
    private final ArrayList<Post> posts;

    public postTableModel(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public int getRowCount() {
        return posts.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Должность";
            case 1 -> "Заработная плата (руб)";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> Integer.class;
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Post post = posts.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> post.getName();
            case 1 -> post.getWage();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Post post = posts.get(rowIndex);
        UIManager.put("OptionPane.yesButtonText", "Сохранить");
        UIManager.put("OptionPane.noButtonText", "Отменить");
        int confirmation;
        String message = "Текущее значение:\n" + getValueAt(rowIndex, columnIndex) + "\nСохраняемое значение:\n" + aValue;
        String title = "Сохранить изменения в базе данных?";
        if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
            if (!Objects.equals(aValue, "")) {
                confirmation = JOptionPane.showConfirmDialog(
                        null,
                        message,
                        title,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    switch (columnIndex) {
                        case 0: //Название
                            try {
                                ClientService.getInstance().getService().updatePostName((String) aValue, post.getPostId());
                                post.setName((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 1: //ЗП
                            try {
                                ClientService.getInstance().getService().updatePostWage((Integer) aValue, post.getPostId());
                                post.setWage((Integer) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                }
            }  else {
                JOptionPane.showMessageDialog(null,
                        "Данные введены некорректно!",
                        "Предупреждение",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
