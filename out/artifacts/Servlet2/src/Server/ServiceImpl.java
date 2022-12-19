package Server;

import api.Services.Service;
import com.caucho.hessian.server.HessianServlet;

public class ServiceImpl extends HessianServlet implements Service{
    @Override
    public int plus(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
