package ntrusted.controllers;

import ntrusted.models.CategoryDao;
import ntrusted.models.User;
import ntrusted.mongoModel.Customer;
import ntrusted.mongo_services.GenerateMongoMapping;
import ntrusted.models.Category;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	private CategoryDao _categoryDao;
	
	@RequestMapping(value="/save")
	  @ResponseBody
	  public String create(int id, String name) {
	    try {
	      Category category = new Category(id, name);
	      _categoryDao.save(category);
	      
	    }
	    catch(Exception ex) {
	      return ex.getMessage();
	    }
	    return "Category succesfully saved!";
	  }
	  
	@RequestMapping(value="/fetchCategory")
	@ResponseBody
	  public String getById(int id) {
	    int categoryId;
	    String categoryName;
	    try {
	      Category category = _categoryDao.getById(id);
	      categoryId = category.getCategoryId();
	      categoryName = category.getCategoryName();
	    }
	    catch(Exception ex) {
	      return "Category not found";
	    }
	    return "The category id is: " + categoryId + "\nThe category Name is: "+ categoryName;
	  } // function end
} //CategoryController Class
