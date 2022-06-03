#http://stackoverflow.com/questions/20188720/python-soap-client-nested-request

from pysimplesoap.client import SoapClient, SoapFault

client = SoapClient(wsdl="http://www.webservicex.net/geoipservice.asmx?WSDL")

result = client.GetGeoIP(("10.0.1.152"))

print(result)
