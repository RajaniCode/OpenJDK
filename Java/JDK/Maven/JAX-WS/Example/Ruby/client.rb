# gem install savon
require 'savon' 

class RubyClient

 def client
  Savon.client(wsdl: "http://localhost:9999/ws/hello?wsdl")
 end
  
 def get_hello_world(hash)
  #client = Savon.client(wsdl: "http://localhost:9999/ws/hello?wsdl") 
  response = client.call(:get_hello_world_as_string, message: hash)
  response.body[:get_hello_world_as_string_response][:return]
 rescue Savon::SOAPFault => error
  fault_code = error.to_hash[:fault][:faultcode]
  raise CustomError, fault_code
 end

end

puts RubyClient.new.get_hello_world(:arg0 => "Ruby Web Service Client")

