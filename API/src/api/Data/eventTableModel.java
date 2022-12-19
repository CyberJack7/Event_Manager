//package api.Data;
//
//import javax.swing.table.AbstractTableModel;
//import java.util.ArrayList;
//
//public class eventTableModel extends AbstractTableModel {
//    private ArrayList<Event> events = new ArrayList<>();
//
//    public eventTableModel(ArrayList<Event> events){
//        this.events = events;
//    }
//
//    @Override
//    public int getRowCount() {
//        return events.size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return 8;
//    }
//
//    @Override
//    public String getColumnName(int columnIndex) {
//        switch (columnIndex){
//            case 0: return "Название";
//            case 1: return "Тематика";
//            case 2: return "Дата";
//            case 3: return "Место";
//            case 4: return "Тип мероприятия";
//            case 5: return "Жанр";
//            case 6: return "Описание";
//            case 7: return "Программа";
//
//        }
//        return null;
//    }
//
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return String.class;
//    }
//
//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return false;
//    }
//
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        Event event = events.get(rowIndex);
//        switch (columnIndex){
//            case 0:
//                return event.getEventName();
//            case 1:
//                if (event.getSubject() == null) {
//                    return "-";
//                }
//                return event.getSubject();
//            case 2:
//                if (event.getDate() == null) {
//                    return "-";
//                }
//                return event.getDate();
//            case 3:
//                if (event.getPlace() == null) {
//                    return "-";
//                }
//                return event.getPlace();
//            case 4:
//                if (event.getEventType() == null) {
//                    return "-";
//                }
//                return event.getEventType();
//            case 5:
//                if (event.getGenre() == null) {
//                    return "-";
//                }
//                return event.getGenre();
//            case 6:
//                if (event.getDescription() == null) {
//                    return "-";
//                }
//                return event.getDescription();
//            case 7:
//                if (event.getProgram() == null) {
//                    return "-";
//                }
//                return event.getProgram();
//        }
//        return null;
//    }
//
//    /*@Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        Event event = events.get(rowIndex);
//        switch (columnIndex){
//            case 0:
//                event.setEventName((String) aValue);
//                break;
//            case 1:
//                event.setSubject((String) aValue);
//                break;
//            case 2:
//                event.setDate((String) aValue);
//                break;
//            case 3:
//                event.setPlace((String) aValue);
//                break;
//            case 4:
//                event.setEventType((String) aValue);
//                break;
//            case 5:
//                event.setGenre((String) aValue);
//                break;
//            case 6:
//                event.setDescription((String) aValue);
//                break;
//            case 7:
//                event.setProgram((String) aValue);
//                break;
//        }
//    }*/
//}