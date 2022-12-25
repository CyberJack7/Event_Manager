package Client;

import Data.Employee;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class employeeTableModel extends AbstractTableModel {
    private final ArrayList<Employee> employees;

    public employeeTableModel(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Фамилия";
            case 1 -> "Имя";
            case 2 -> "Отчество";
            case 3 -> "Эл. почта";
            case 4 -> "Телефон";
            case 5 -> "Дата рождения";
            case 6 -> "Дата приёма на работу";
            case 7 -> "Должность";
            case 8 -> "Дата увольнения";
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
        Employee employee = employees.get(rowIndex);
        switch (columnIndex){
            case 0:
                return employee.getSurname();
            case 1:
                return employee.getName();
            case 2:
                if (employee.getPatronymic() == null | Objects.equals(employee.getPatronymic(), "")) {
                    return "-";
                }
                return employee.getPatronymic();
            case 3:
                return employee.getEmail();
            case 4:
                return employee.getPhone();
            case 5:
                return employee.getDateBirth();
            case 6:
                return employee.getEmploymentDate();
            case 7:
                if (employee.getPost() == null | Objects.equals(employee.getPost(), "")) {
                    return "-";
                }
                return employee.getPost();
            case 8:
                if (employee.getDismissalDate() == null) {
                    return "-";
                }
                return employee.getDismissalDate();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        UIManager.put("OptionPane.yesButtonText", "Сохранить");
        UIManager.put("OptionPane.noButtonText", "Отменить");
        int confirmation;
        String message = "Текущее значение:\n" + getValueAt(rowIndex, columnIndex) + "\nСохраняемое значение:\n" + aValue;
        String title = "Сохранить изменения в базе данных?";
        if (!Objects.equals(getValueAt(rowIndex, columnIndex), aValue)) {
            if ((!Objects.equals(aValue, "") & columnIndex != 2) | columnIndex == 2) {
                confirmation = JOptionPane.showConfirmDialog(
                        null,
                        message,
                        title,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirmation == JOptionPane.YES_OPTION) {
                    switch (columnIndex) {
                        case 0: //Фамилия
                            try {
                                ClientService.getInstance().getService().updateEmployeeSurname((String) aValue, employee.getEmployeeId());
                                employee.setSurname((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 1: //Имя
                            try {
                                ClientService.getInstance().getService().updateEmployeeName((String) aValue, employee.getEmployeeId());
                                employee.setName((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 2: //Отчество
                            try {
                                ClientService.getInstance().getService().updateEmployeePatronymic((String) aValue, employee.getEmployeeId());
                                employee.setPatronymic((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 3: //Эл. почта
                            try {
                                ClientService.getInstance().getService().updateEmployeeEmail((String) aValue, employee.getEmployeeId());
                                employee.setEmail((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 4: //Телефон
                            try {
                                ClientService.getInstance().getService().updateEmployeePhone((String) aValue, employee.getEmployeeId());
                                employee.setPhone((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 5: //Дата рождения
                            try {
                                ClientService.getInstance().getService().updateEmployeeDateBirth((String) aValue, employee.getEmployeeId());
                                employee.setDateBirth((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 6: //Дата приёма на работу
                            try {
                                ClientService.getInstance().getService().updateEmployeeEmploymentDate((String) aValue, employee.getEmployeeId());
                                employee.setEmploymentDate((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 7: //Должность
                            try {
                                ClientService.getInstance().getService().updateEmployeePost((String) aValue, employee.getEmployeeId());
                                employee.setPost((String) aValue);
                            } catch (MalformedURLException | SQLException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 8: //Дата увольнения
                            try {
                                ClientService.getInstance().getService().updateDismissalDate((String) aValue, employee.getEmployeeId());
                                employee.setDismissalDate((String) aValue);
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
