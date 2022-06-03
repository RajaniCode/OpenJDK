package ws;

import javax.jws.WebService;
 
//Service Implementation
@WebService(endpointInterface = "ws.JavaWebService")
public class JavaWebServiceImp implements JavaWebService {
    @Override
    public String getData(String data) {
        return "Java Web Service: " + data;
    } 
}
