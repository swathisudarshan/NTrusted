package ntrusted.controllers;

import ntrusted.mongoModel.Customer;
import ntrusted.models.User;
import ntrusted.models.UserDao;
import ntrusted.mongo_services.GenerateMongoMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/user")
public class UserController {

  @Autowired
  private UserDao _userDao;

  @Autowired
  private CustomerRepository cuRepo;
  
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(String id) {
    try {
      User user = new User(id);
      _userDao.delete(user);
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully deleted!";
  }
  
  @RequestMapping(value="/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    try {
      User user = _userDao.getByEmail(email);
      userId = String.valueOf(user.getFbId());
    }
    catch(Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId;
  }
 /* 
  @RequestMapping(value="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String id, @RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		//MySQL
		System.out.println("***Name :"+name+" Email: "+email);
		try{
			User user = new User(id,name,email);
			userDao.Create(user);
		}
		catch(Exception ex){
			return ex.getMessage();
		}
		
		//Mongo
		GenerateMongoMapping mapping  = new GenerateMongoMapping();
		Customer cus1 = mapping.generateMongoJson();
				
		cuRepo.save(cus1);
			    
		getCustomerByUserId(123);
		return "Saved";
	}
	
	
*/
  
  

  @RequestMapping(value="/save")
  @ResponseBody
  public String create(String id, String name, String email, String phone) {
    try {
      User user = new User(id, name, email, phone);
      _userDao.save(user);
      
    //Mongo
    		GenerateMongoMapping mapping  = new GenerateMongoMapping();
    		Customer cus1 = mapping.generateMongoJson();
    				
    		cuRepo.save(cus1);
    			    
    		getCustomerByUserId(123);
      //Customer cus1=new Customer(name,email);
      //cuRepo.save(cus1);
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully saved!";
  }
  
  public Customer getCustomerByUserId(final int userId){
		System.out.println("*************"+cuRepo.findByUserId(userId));
		return cuRepo.findByUserId(userId);
		
	}

} // class UserController
