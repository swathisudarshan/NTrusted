package ntrusted.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ntrusted.models.User;
import ntrusted.models.UserDao;
import ntrusted.mongoModel.Customer;
import ntrusted.mongo_services.GenerateMongoMapping;

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

  @RequestMapping(value="/signup")
  @ResponseBody
  public String create(String id, String name, String email, String phone) {
    try {
      User user = new User(id, name, email, phone);
      _userDao.save(user);
      
    
      //Mongo
    		GenerateMongoMapping mapping  = new GenerateMongoMapping();
    		//List<String> list1 = new ArrayList<String>();
    		FacebookFriendList friendClassObject = new FacebookFriendList();
    		List<String> list1 = friendClassObject.getFriends(id);
    		
    		Customer cus1 = mapping.generateMongoJson(id,list1);
    				
    		cuRepo.save(cus1);	

    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully saved!";
  }
  
  public Customer getCustomerByUserId(final String userId){
		System.out.println("*************"+cuRepo.findByCustomerId(userId));
		return cuRepo.findByCustomerId(userId);
		
	}

} // class UserController
