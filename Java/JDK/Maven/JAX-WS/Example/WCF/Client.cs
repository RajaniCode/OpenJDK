using static System.Console;
using System.ServiceModel;

class Client 
{
    static void Main() 
    {
	HelloWorldClient port = null;
    	try 
	{
      	    port = new HelloWorldClient("HelloWorldImplPort");
      	    WriteLine(port.getHelloWorldAsString("WCF Client"));      
      	    port.Close();
    	} 
	catch (FaultException e) 
	{
      	    WriteLine("Exception: " + e.Message);
      	    if (port != null)
            { 
                port.Close();
	    }
    	}
    }
} 