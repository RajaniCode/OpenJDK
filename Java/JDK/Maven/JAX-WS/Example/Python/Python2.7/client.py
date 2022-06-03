# pip install suds
from __future__ import print_function

import suds
from suds.client import Client

class ServiceClient(object):

    def getHelloWorld(self, arg):
        try:
            wsdl_url = 'http://localhost:9999/ws/hello?wsdl'
            client = Client(wsdl_url)
            # print client
            result = client.service.getHelloWorldAsString(arg)
            return result
        except suds.WebFault as f:
            print(f)
            print(f.fault)
        except Exception as e:
            print(e)

print (ServiceClient().getHelloWorld('Python Web Service Client'))


