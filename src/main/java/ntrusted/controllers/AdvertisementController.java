package ntrusted.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ntrusted.models.Advertisement;
import ntrusted.models.AdvertisementDao;
import ntrusted.models.Category;
import ntrusted.models.CategoryDao;
import ntrusted.models.Transaction;
import ntrusted.models.User;
import ntrusted.models.UserDao;

@Controller
@RequestMapping(value="/advertisement")
public class AdvertisementController {
	 @Autowired
	  private AdvertisementDao _adDao;
	 
	 @Autowired 
	 private CategoryDao _catDao;
	 
	 
	 @Autowired
	 private UserDao _userDao;
	 
	 @RequestMapping(value="/delete")
	  @ResponseBody
	  public String delete(int id) {
	    try {
	      Advertisement advertisement = new Advertisement(id);
	      _adDao.delete(advertisement);
	    }
	    catch(Exception ex) {
	      return ex.getMessage();
	    }
	    return "Advertisement succesfully deleted!";
	  }
	 
	 @RequestMapping(value="/getAllAds")
	  @ResponseBody
	  public List<Advertisement> getAllAds() {
	      return _adDao.getAll();
	    }
	   
	 @RequestMapping(value="/getAllBorrowAds")
	  @ResponseBody
	  public List<Advertisement> getAllBorrowAds() {
	      return _adDao.getAllBorrowAds();
	    }
	 
	 @RequestMapping(value="/getAllLendingAds")
	  @ResponseBody
	  public List<Advertisement> getAllLendingAds() {
	      return _adDao.getAllLendAds();
	    }
	 
	 @RequestMapping(value="/addLendingProduct", method=RequestMethod.POST)
	  @ResponseBody
	  public String createLendingAdvertisement(@RequestParam(value="productName")String productName, 
			  @RequestParam(value="productDescription") String productDescription, 
			  @RequestParam(value="productPrice") String productPrice, 
			  @RequestParam(value="active") String active, 
			  @RequestParam(value="categoryId") String categoryId, 
			  @RequestParam(value="userId") String userId,
			  @RequestParam(value="adType") String adType) {
	    try {
	
	    	
	    	System.out.println("In Add lending product !!!!!!!!!!!!!!!!");
	    	
	    	//System.out.println("The parameters are : "+productName+" "+productDescription+" "+productPrice+" "+active+" "+categoryId+" "+userId+" "+adType);
	    	
	    	float productpriceFloat = Float.valueOf(productPrice);
	    	int activeInteger = Integer.valueOf(active);
	    	int categoryInteger = Integer.valueOf(categoryId);
	    	int adTypeInteger = Integer.valueOf(adType);
	    	
	    	Category category = _catDao.getById(categoryInteger);
	    	User user = _userDao.getById(userId);
	    	java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
	    	//System.out.println("Product Name:"+productName+" Product Desc: "+productDescription+" Product Price:"+productpriceFloat+" Active: "+activeInteger+" Category:"+category.getCategoryName()+" Ad Type:"+adTypeInteger+" User:"+user.getName());
	    	Advertisement ad = new Advertisement(productName, productDescription, productpriceFloat,date,activeInteger,category,user,adTypeInteger);
	     	 _adDao.save(ad);
			}
			catch(Exception ex) {
			      return ex.getMessage();
			    }
	    return "Lending Advertisement saved!";
	  }
	 
	 //Add Borrowing ad
	 @RequestMapping(value="/addBorrowingProduct", method=RequestMethod.POST)
	  @ResponseBody
	  public String createBorrowingAdvertisement(@RequestParam(value="productName")String productName, 
			  @RequestParam(value="productDescription") String productDescription, 
			  @RequestParam(value="active") String active, 
			  @RequestParam(value="categoryId") String categoryId, 
			  @RequestParam(value="userId") String userId,
			  @RequestParam(value="adType") String adType) {
	    try {
	    	//Category category = new Category(categoryId);
	    	
	    	System.out.println("In Add lending product !!!!!!!!!!!!!!!!");
	    	
	    	System.out.println("The parameters are : "+productName+" "+productDescription+" "+active+" "+categoryId+" "+userId+" "+adType);
	 
	    	int activeInteger = Integer.valueOf(active);
	    	int categoryInteger = Integer.valueOf(categoryId);
	    	int adTypeInteger = Integer.valueOf(adType);  	
	    	
	    	Category category = _catDao.getById(categoryInteger);
	    	User user = _userDao.getById(userId);
	    	java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
	    	
	    	Advertisement ad = new Advertisement(productName, productDescription,0,date,activeInteger,category,user,adTypeInteger);
				 _adDao.save(ad);
			}
			catch(Exception ex) {
			      return ex.getMessage();
			    }
	
	    return "Borrowing Advertisement saved!";
	  }
}
