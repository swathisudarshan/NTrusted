package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.Product;
import hello.ProductRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/product") // This means URL's start with /demo (after Application path)
public class ProductController {

 
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private ProductRepository productRepository;
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewProduct (@RequestParam String userId
			, @RequestParam String productName
			, @RequestParam String productDescription
			, @RequestParam float productPrice
			, @RequestParam float productRating) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Product n = new Product();
		n.setUser_id(userId);
		n.setProduct_name(productName);
		n.setProduct_description(productDescription);
		n.setProduct_price(productPrice);
		n.setProduct_rating(productRating);
		productRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Product> getAllUsers() {
		// This returns a JSON or XML with the users
		
		return productRepository.findAll();
		
	}
}