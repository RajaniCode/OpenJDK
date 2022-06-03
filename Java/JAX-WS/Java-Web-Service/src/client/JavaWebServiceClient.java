package client;

import static java.lang.System.out;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import ws.JavaWebService;

public class JavaWebServiceClient {
    public static void main(String[] args) throws Exception {
        URL location = new URL("http://localhost:8000/JavaWebService?wsdl");
        QName name = new QName("http://ws/", "JavaWebServiceImpService");
        Service service = Service.create(location, name);
        JavaWebService port = service.getPort(JavaWebService.class);
        out.println(port.getData("Java Web Service Client"));
    }
}