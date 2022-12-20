package Client;

import Data.Event;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class eventTableModel extends AbstractTableModel {
    private final ArrayList<Event> events;

    public eventTableModel(ArrayList<Event> events){
        this.events = events;
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Название";
            case 1 -> "Тематика";
            case 2 -> "Дата";
            case 3 -> "Место";
            case 4 -> "Тип мероприятия";
            case 5 -> "Жанр";
            case 6 -> "Описание";
            case 7 -> "Программа";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event event = events.get(rowIndex);
        switch (columnIndex){
            case 0:
                return event.getEventName();
            case 1:
                if (event.getSubject() == null) {
                    return "-";
                }
                return event.getSubject();
            case 2:
                if (event.getDate() == null) {
                    return "-";
                }
                return event.getDate();
            case 3:
                if (event.getPlace() == null) {
                    return "-";
                }
                return event.getPlace();
            case 4:
                if (event.getEventType() == null) {
                    return "-";
                }
                return event.getEventType();
            case 5:
                if (event.getGenre() == null) {
                    return "-";
                }
                return event.getGenre();
            case 6:
                if (event.getDescription() == null) {
                    return "-";
                }
                return event.getDescription();
            case 7:
                if (event.getProgram() == null) {
                    return "-";
                }
                return event.getProgram();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Event event = events.get(rowIndex);
        UIManager.put("OptionPane.yesButtonText", "Сохранить");
        UIManager.put("OptionPane.noButtonText", "Отменить");
        int confirmation;
        String message = "Текущее значение:\n" + getValueAt(rowIndex, columnIndex) + "\nСохраняемое значение:\n" + aValue;
        String title = "Сохранить изменения в базе данных?";
        switch (columnIndex){
            case 0: //название
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
                            event.setEventName((String) aValue);
                            try {
                                Event.updateEventName((String) aValue, event.getEventId());
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Нельзя сохранить мероприятие без названия!",
                                "Предупреждение",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case 1: //тематика
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setSubject((String) aValue);
                        try {
                            Event.updateSubject((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 2: //дата
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setDate((String) aValue);
                        try {
                            Event.updateDate((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 3: //место
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setPlace((String) aValue);
                        try {
                            Event.updatePlace((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 4: //тип
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setEventType((String) aValue);
                        try {
                            Event.updateEventType((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 5: //жанр
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setGenre((String) aValue);
                        try {
                            Event.updateGenre((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 6: //описание
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setDescription((String) aValue);
                        try {
                            Event.updateDescription((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
            case 7: //программа
                if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
                    confirmation = JOptionPane.showConfirmDialog(
                            null,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmation == JOptionPane.YES_OPTION) {
                        event.setProgram((String) aValue);
                        try {
                            Event.updateProgram((String) aValue, event.getEventId());
                        } catch (MalformedURLException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;
        }
    }
}