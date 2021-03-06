package ntrusted.mongo_services;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ntrusted.mongoModel.Connection;
import ntrusted.mongoModel.Customer;

public class GenerateMongoMapping {

	public Customer generateMongoJson(String id,List<String> list)
	{
		ObjectMapper mapper = new ObjectMapper();
		Customer dummyCustomer = createCustomerJson(id,list);
		
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
	private Customer createCustomerJson(String id,List<String> list)
	{
		//List<Connection> list1 = new ArrayList<Connection>();
		Customer customer = new Customer(); 
		customer.setUserid(id);
	    //customer.setConnections(list1);
		for(String str:list)
		{
			Connection con =  new Connection();
			con.setUserid(str);
			con.setRenteeNoTrx(0);
			con.setRenterNoTrx(0);
			con.setRenterTrust(0);
			con.setRenteTrust(0);
			con.setActiveFriend(1);
			customer.getConnections().add(con);
		}	
		
	    return customer;
	}
	
	public Customer updateCustomer(Customer cus,List<String> list)
	{
		List<Connection> cons = cus.getConnections();
		
		for(Connection con: cons)
		{
			if(list.contains(con.getUserid()))
			{
				con.setActiveFriend(1);
				list.remove(con.getUserid());
			}
		}
		
		if(list.size()!=0)
		{
			for(String str:list)
			{
				Connection con =  new Connection();
				con.setUserid(str);
				con.setRenteeNoTrx(0);
				con.setRenterNoTrx(0);
				con.setRenterTrust(0);
				con.setRenteTrust(0);
				con.setActiveFriend(1);
				cus.getConnections().add(con);
			}	
		}
		
		return cus;
	}
	
	
}
