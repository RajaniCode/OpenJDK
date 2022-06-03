<?php 

// http://php.net/manual/en/class.soapclient.php
// Create the SoapClient instance 
$url         = "http://localhost:9999/ws/hello?wsdl"; 
$client     = new SoapClient($url, array("trace" => 1, "exception" => 0)); 

// Call wsdl function 
$result = $client->__soapCall("getHelloWorldAsString", array('arg0' => "PHP Web Service Client")); 

// Print the result 
print_r($result);

?>