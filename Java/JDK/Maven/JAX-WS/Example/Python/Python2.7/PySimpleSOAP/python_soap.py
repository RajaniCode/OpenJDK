from pysimplesoap.client import SoapClient, SoapFault

client = SoapClient(wsdl="http://www.webservicex.net/geoipservice.asmx?WSDL")

result = client.GetGeoIP(("10.0.1.152"))

print(result)
