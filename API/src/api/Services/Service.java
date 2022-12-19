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
}
