package com.learn.ecom.services.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.ecom.models.Customer;
import com.learn.ecom.repository.CustomerRepository;
import com.learn.ecom.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Logic to fetch all customers from the database
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        // Logic to fetch a customer by ID from the database
        return this.customerRepository.findById(id).orElse(null);
    }
    
    @Override
    public Customer createCustomer(Customer customer) {
        // Logic to create a new customer in the database
        return this.customerRepository.save(customer);
    }
    
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        // Logic to update an existing customer in the database
        if (this.customerRepository.existsById(id)) {
            customer.setId(id);
            this.customerRepository.save(customer);
            return this.customerRepository.findById(id).orElse(null);
        }
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        // Logic to delete a customer from the database
        this.customerRepository.deleteById(id);
    }
    

}
