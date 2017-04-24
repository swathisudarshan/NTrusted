package ntrusted.controllers;

import ntrusted.mongoModel.Customer;
import ntrusted.models.User;
import ntrusted.models.UserDao;
import ntrusted.mongo_services.GenerateMongoMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value="/signup", method=RequestMethod.POST)
  @ResponseBody
  public String create(@RequestParam(value="id")String id,
		  			   @RequestParam(value="name") String name,
		  			   @RequestParam(value="email") String email,
		  			   @RequestParam(value="phone") String phone, 
		  			   @RequestParam(value="address") String address) {
    try {
      User user = new User(id, name, email, phone, address);
      _userDao.save(user);
      
    //Mongo
		GenerateMongoMapping mapping  = new GenerateMongoMapping();
		FacebookFriendList friendClassObject = new FacebookFriendList();
		List<String> list1 = friendClassObject.getFriends(id);
		
		Customer cus1 = mapping.generateMongoJson(id,list1);
    				
    	cuRepo.save(cus1);
    			    
    	System.out.println(getCustomerByUserId(id));
     
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully saved!";
  }
  
  
  
  @RequestMapping(value="/signin")
  @ResponseBody
  public String signIn(String id) {
    try {
     
      User user = _userDao.getById(id);
      
    //Mongo
		GenerateMongoMapping mapping  = new GenerateMongoMapping();
		FacebookFriendList friendClassObject = new FacebookFriendList();
		List<String> list1 = friendClassObject.getFriends(id);
		list1.add("123456");
		Customer cus1 = mapping.updateCustomer(cuRepo.findByUserId(id),list1);
    		System.out.println("UPdated customer is : "+cus1.toString());		
    	cuRepo.save(cus1);
    			    
    	System.out.println(getCustomerByUserId(id));
      //Customer cus1=new Customer(name,email);
      //cuRepo.save(cus1);
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully saved!";
  }
  
  public Customer getCustomerByUserId(final String userId){
		System.out.println("*************"+cuRepo.findByUserId(userId));
		return cuRepo.findByUserId(userId);
		
	}

} // class UserController
