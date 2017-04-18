package ntrusted.controllers;

import org.springframework.data.mongodb.repository.MongoRepository;
import ntrusted.mongoModel.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {

    public Customer findByUserId(int userId);
   // public List<Customer> findByLastName(String lastName);

}