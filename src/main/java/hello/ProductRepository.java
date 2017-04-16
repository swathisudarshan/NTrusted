package hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hello.Product;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Long> {
		// find 
	
	
}