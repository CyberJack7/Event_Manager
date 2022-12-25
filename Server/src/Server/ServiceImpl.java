package Server;

import Data.Employee;
import Data.Event;

import Data.Post;
import api.Services.Service;
import com.caucho.hessian.server.HessianServlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceImpl extends HessianServlet implements Service{

    @Override
    public ArrayList<Integer> getEventsId() throws SQLException {
        return Queries.getEventsId();
    }

    @Override
    public HashMap<String, String> getEventById(int event_id) throws SQLException {
        return EventDBManager.getEventById(event_id);
    }

    @Override
    public int addEventInDB(Event event) throws SQLException {
        return EventDBManager.addEventInDB(event);
    }

    @Override
    public void deleteEventFromDB(ArrayList<Event> deletedEvents) throws SQLException {
        EventDBManager.deleteEventFromDB(deletedEvents);
    }

    @Override
    public String[] getGenres() throws SQLException {
        return Queries.getGenres();
    }

    @Override
    public String[] getEventTypes() throws SQLException {
        return Queries.getEventTypes();
    }

    @Override
    public void updateEventName(String eventName, int eventId) throws SQLException {
        EventDBManager.updateEventName(eventName, eventId);
    }

    @Override
    public void updateSubject(String subject, int eventId) throws SQLException {
        EventDBManager.updateSubject(subject, eventId);
    }

    @Override
    public void updateDate(String date, int eventId) throws SQLException {
        EventDBManager.updateDate(date, eventId);
    }

    @Override
    public void updatePlace(String place, int eventId) throws SQLException {
        EventDBManager.updatePlace(place, eventId);
    }

    @Override
    public void updateEventType(String eventType, int eventId) throws SQLException {
        EventDBManager.updateEventType(eventType, eventId);
    }

    @Override
    public void updateGenre(String genre, int eventId) throws SQLException {
        EventDBManager.updateGenre(genre, eventId);
    }

    @Override
    public void updateDescription(String description, int eventId) throws SQLException {
        EventDBManager.updateDescription(description, eventId);
    }

    @Override
    public void updateProgram(String program, int eventId) throws SQLException {
        EventDBManager.updateProgram(program, eventId);
    }

    @Override
    public ArrayList<Integer> getStaffId() throws SQLException {
        return Queries.getStaffId();
    }

    @Override
    public HashMap<String, String> getEmployeeById(int employee_id) throws SQLException {
        return EmployeeDBManager.getEmployeeById(employee_id);
    }

    @Override
    public String[] getPosts() throws SQLException {
        return Queries.getPosts();
    }

    @Override
    public int addEmployeeInDB(Employee employee) throws SQLException {
        return EmployeeDBManager.addEmployeeInDB(employee);
    }

    @Override
    public void deleteEmployeesFromDB(ArrayList<Employee> deleted_employees) throws SQLException {
        EmployeeDBManager.deleteEmployeesInDB(deleted_employees);
    }

    @Override
    public void updateEmployeeSurname(String surname, int employeeId) throws SQLException {
        EmployeeDBManager.updateSurname(surname, employeeId);
    }

    @Override
    public void updateEmployeeName(String name, int employeeId) throws SQLException {
        EmployeeDBManager.updateName(name, employeeId);
    }

    @Override
    public void updateEmployeePatronymic(String patronymic, int employeeId) throws SQLException {
        EmployeeDBManager.updatePatronymic(patronymic, employeeId);
    }

    @Override
    public void updateEmployeeEmail(String email, int employeeId) throws SQLException {
        EmployeeDBManager.updateEmail(email, employeeId);
    }

    @Override
    public void updateEmployeePhone(String phone, int employeeId) throws SQLException {
        EmployeeDBManager.updatePhone(phone, employeeId);
    }

    @Override
    public void updateEmployeeDateBirth(String dateBirth, int employeeId) throws SQLException {
        EmployeeDBManager.updateDateBirth(dateBirth, employeeId);
    }

    @Override
    public void updateEmployeeEmploymentDate(String employmentDate, int employeeId) throws SQLException {
        EmployeeDBManager.updateEmploymentDate(employmentDate, employeeId);
    }

    @Override
    public void updateEmployeePost(String post, int employeeId) throws SQLException {
        EmployeeDBManager.updatePost(post, employeeId);
    }

    @Override
    public void updateDismissalDate(String dismissalDate, int employeeId) throws SQLException {
        EmployeeDBManager.updateDismissalDate(dismissalDate, employeeId);
    }

    @Override
    public ArrayList<Integer> getPostsId() throws SQLException {
        return Queries.getPostsId();
    }

    @Override
    public Post getPostById(int post_id) throws SQLException {
        return PostDBManager.getPostById(post_id);
    }

    @Override
    public void updatePostName(String name, int postId) throws SQLException {
        PostDBManager.updatePostName(name, postId);
    }

    @Override
    public void updatePostWage(Integer wage, int postId) throws SQLException {
        PostDBManager.updatePostWage(wage, postId);
    }

    @Override
    public void deletePostsFromDB(ArrayList<Post> deleted_posts) throws SQLException {
        PostDBManager.deletePostsFromDB(deleted_posts);
    }

    @Override
    public int addPostInDB(Post post) throws SQLException {
        return PostDBManager.addPostInDB(post);
    }
}
