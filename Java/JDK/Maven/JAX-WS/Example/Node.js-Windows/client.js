// npm install soap // or // npm i soap
var soap = require('soap');
var url = 'http://localhost:9999/ws/hello?wsdl';
var args = {arg0: 'Node.js Web Service Client'};
soap.createClient(url, function(err, client) {
  // console.log(client);
  client.getHelloWorldAsString(args, function(err, result) {
    console.log(result["return"]);
  });
});
