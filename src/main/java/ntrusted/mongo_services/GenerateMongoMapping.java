package ntrusted.mongo_services;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ntrusted.mongoModel.Customer;
import ntrusted.mongoModel.Connection;

public class GenerateMongoMapping {

	public Customer generateMongoJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		Customer dummyCustomer = createCustomerJson();
		
		try{
			
			String normalView  = mapper.writeValueAsString(dummyCustomer);
			System.out.println("NESTED JSON"+normalView);
			
		}catch(JsonGenerationException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return dummyCustomer;
		
	}
	private Customer createCustomerJson()
	{
		//List<Connection> list1 = new ArrayList<Connection>();
		Customer customer = new Customer(); 
		customer.setUserid(123);
	    //customer.setConnections(list1);
		
		Connection con =  new Connection();
		con.setUserid(123);
		con.setRenteeNoTrx(0);
		con.setRenterNoTrx(0);
		con.setRenterTrust(0.0);
		con.setRenteTrust(0.0);
		customer.getConnections().add(con);
		customer.setActiveFriend(0);
		
	    return customer;
	}
	
	
}
