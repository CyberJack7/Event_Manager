package api.Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Service {

    ArrayList<Integer> getEventsId() throws SQLException;

    HashMap<String, String> getEventById(int event_id) throws SQLException;

    int addEventInDB(HashMap<String, String> event) throws SQLException;

    void deleteEventFromDB(ArrayList<Integer> deletedEvents) throws SQLException;

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
}
