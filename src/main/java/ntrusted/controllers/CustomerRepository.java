package ntrusted.controllers;

import org.springframework.data.mongodb.repository.MongoRepository;
import ntrusted.mongoModel.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByCustomerId(String userId);
   // public List<Customer> findByLastName(String lastName);

}