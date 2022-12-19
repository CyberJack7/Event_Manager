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
}
