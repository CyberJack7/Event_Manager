package Server;

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
    public int addEventInDB(HashMap<String, String> event) throws SQLException {
        return EventDBManager.addEventInDB(event);
    }

    @Override
    public void deleteEventFromDB(ArrayList<Integer> deletedEvents) throws SQLException {
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
}
