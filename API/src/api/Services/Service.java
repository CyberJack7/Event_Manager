package api.Services;

import Data.Employee;
import Data.Event;
import Data.Post;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Service {

    ArrayList<Integer> getEventsId() throws SQLException;

    HashMap<String, String> getEventById(int event_id) throws SQLException;

    int addEventInDB(Event event) throws SQLException;

    void deleteEventFromDB(ArrayList<Event> deletedEvents) throws SQLException;

    String[] getGenres() throws SQLException;

    String[] getEventTypes() throws SQLException;

    void updateEventName(String eventName, int eventId) throws SQLException;

    void updateSubject(String subject, int eventId) throws SQLException;

    void updateDate(String date, int eventId) throws SQLException;

    void updatePlace(String place, int eventId) throws SQLException;

    void updateEventType(String eventType, int eventId) throws SQLException;

    void updateGenre(String genre, int eventId) throws SQLException;

    void updateDescription(String description, int eventId) throws SQLException;

    void updateProgram(String program, int eventId) throws SQLException;

    ArrayList<Integer> getStaffId() throws SQLException;

    HashMap<String, String> getEmployeeById(int employee_id) throws SQLException;

    String[] getPosts() throws SQLException;

    int addEmployeeInDB(Employee employee) throws SQLException;

    void deleteEmployeesFromDB(ArrayList<Employee> deleted_employees) throws SQLException;

    void updateEmployeeSurname(String aValue, int employeeId) throws SQLException;

    void updateEmployeeName(String aValue, int employeeId) throws SQLException;

    void updateEmployeePatronymic(String aValue, int employeeId) throws SQLException;

    void updateEmployeeEmail(String aValue, int employeeId) throws SQLException;

    void updateEmployeePhone(String aValue, int employeeId) throws SQLException;

    void updateEmployeeDateBirth(String aValue, int employeeId) throws SQLException;

    void updateEmployeeEmploymentDate(String aValue, int employeeId) throws SQLException;

    void updateEmployeePost(String aValue, int employeeId) throws SQLException;

    void updateDismissalDate(String aValue, int employeeId) throws SQLException;

    ArrayList<Integer> getPostsId() throws SQLException;

    Post getPostById(int post_id) throws SQLException;

    void updatePostName(String aValue, int postId) throws SQLException;

    void updatePostWage(Integer aValue, int postId) throws SQLException;

    void deletePostsFromDB(ArrayList<Post> deleted_posts) throws SQLException;

    int addPostInDB(Post post) throws SQLException;
}
