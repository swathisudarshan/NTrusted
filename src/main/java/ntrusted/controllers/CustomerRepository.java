package ntrusted.controllers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ntrusted.mongoModel.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByUserId(String userId);
   // public List<Customer> findByLastName(String lastName);

}