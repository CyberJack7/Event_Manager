package api.Services;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Service {

    ArrayList<Integer> getEventsId() throws SQLException;
}
