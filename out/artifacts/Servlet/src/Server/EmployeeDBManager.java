package Server;

import Data.Employee;
import Data.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EmployeeDBManager {

    public static HashMap<String,String> getEmployeeById(int employee_id) throws SQLException {
        HashMap<String,String> employee = new HashMap<>();
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_query = stmt.executeQuery( "SELECT * FROM public.employee WHERE employee_id = " + employee_id);

        while (event_query.next()) {
            employee.put("email", event_query.getString("email"));
            employee.put("name", event_query.getString("name"));
            employee.put("surname", event_query.getString("surname"));
            employee.put("patronymic", event_query.getString("patronymic"));
            employee.put("dateBirth", event_query.getString("date_birth"));
            employee.put("phone", event_query.getString("phone"));
            employee.put("employmentDate", event_query.getString("employment_date"));
            employee.put("post", Queries.getPostName(event_query.getInt("post_id")));
            employee.put("dismissalDate", event_query.getString("dismissal_date"));
        }
        event_query.close();
        stmt.close();
        return employee;
    }

    public static int addEmployeeInDB(Employee employee) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        String sql = "INSERT INTO public.employee (email,name,surname,patronymic,date_birth,phone,employment_date,post_id,dismissal_date) "
                + "VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, employee.getEmail());
        pstmt.setString(2, employee.getName());
        pstmt.setString(3, employee.getSurname());
        if (!Objects.equals(employee.getPatronymic(), "")) {
            pstmt.setString(4, employee.getPatronymic());
        } else {
            pstmt.setNull(4, java.sql.Types.NULL);
        }
        pstmt.setString(5, employee.getDateBirth());
        pstmt.setString(6, employee.getPhone());
        pstmt.setString(7, employee.getEmploymentDate());
        if (!Objects.equals(employee.getPost(), "Не выбрано")) {
            pstmt.setInt(8, Queries.getPostId(employee.getPost()));
        } else {
            pstmt.setNull(8, java.sql.Types.NULL);
        }
        pstmt.setNull(9, java.sql.Types.NULL);

        pstmt.executeUpdate();
        ResultSet keys_query = pstmt.getGeneratedKeys();
        int employee_id = 0;
        while (keys_query.next()) {
            employee_id = keys_query.getInt("employee_id");
        }
        pstmt.close();
        return employee_id;
    }

    public static void deleteEmployeesInDB(ArrayList<Employee> deleted_employees) throws SQLException {
        for (Employee employee : deleted_employees) {
            Connection conn = DBManager.getInstance().getConn();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.employee WHERE employee_id = " + employee.getEmployeeId();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void updateSurname(String surname, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET surname = '" + surname + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateName(String name, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET name = '" + name + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updatePatronymic(String patronymic, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(patronymic, "")) {
            sql = "UPDATE public.employee SET patronymic = '" + patronymic + "' WHERE employee_id = " + employeeId;
        } else {
            sql = "UPDATE public.employee SET patronymic = NULL WHERE employee_id = " + employeeId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateEmail(String email, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET email = '" + email + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updatePhone(String phone, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET phone = '" + phone + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateDateBirth(String dateBirth, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET date_birth = '" + dateBirth + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateEmploymentDate(String employmentDate, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET employment_date = '" + employmentDate + "' WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updatePost(String post, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET post_id = " + Queries.getPostId(post) + " WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateDismissalDate(String dismissalDate, int employeeId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.employee SET dismissal_date = " + dismissalDate + " WHERE employee_id = " + employeeId;
        stmt.executeUpdate(sql);
        stmt.close();
    }
}
