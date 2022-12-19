package Client;

import api.Services.Service;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;
import java.sql.SQLException;

public class TestClient {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080/Service";

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        Service service = (Service) factory.create(Service.class, url);

        try {
            System.out.println(service.getEventsId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
