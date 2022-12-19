package Server;

import api.Services.Service;
import com.caucho.hessian.server.HessianServlet;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceImpl extends HessianServlet implements Service{

    @Override
    public ArrayList<Integer> getEventsId() throws SQLException {
        return Queries.getEventsId();
    }
}
