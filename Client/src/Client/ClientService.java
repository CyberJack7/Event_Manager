package Client;

import api.Services.Service;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Arrays;

public class ClientService {
    private static ClientService clientService;
    private final Service service;
    public ClientService() throws MalformedURLException {
        String url = "http://localhost:8080/Service";

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        service = (Service) factory.create(Service.class, url);
    }

    public static synchronized ClientService getInstance() throws MalformedURLException {
        if (clientService == null) {
            clientService = new ClientService();
        }
        return clientService;
    }

    public Service getService() throws MalformedURLException {
        return service;
    }

    public static void main(String[] args) throws MalformedURLException, SQLException {
        String url = "http://localhost:8080/Service";

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        Service service_2 = (Service) factory.create(Service.class, url);

        System.out.println(service_2.getEventsId());
        System.out.println(service_2.getEventsId());
    }
}
