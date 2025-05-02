package com.learn.ecom.beans;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.learn.ecom.models.Customer;
import com.learn.ecom.repository.CustomerRepository;

@Component
public class LoadStaticData implements CommandLineRunner {
    
    
    private final CustomerRepository customerRepository;

 
    public LoadStaticData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to prevent duplicates (Optional but recommended)
        // You'd need a method like findByUsername or existsByUsername in your repository
        if (customerRepository.findByUsername("johndoe") == null) { // Assuming findByUsername exists
            Customer customer1 = Customer.builder()
                .username("johndoe")
                .email("john.doe@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St, Springfield")
                .phoneNumber("123-456-7890")
                .build();
            this.customerRepository.save(customer1);
        }


        if (customerRepository.findByUsername("janesmith") == null) { // Assuming findByUsername exists
            Customer customer2 = Customer.builder()
                .username("janesmith")
                .email("jane.smith@example.com")
                .password("password456")
                .firstName("Jane")
                .lastName("Smith")
                .address("456 Elm St, Springfield")
                .phoneNumber("987-654-3210")
                .build();
            this.customerRepository.save(customer2);
        }


        System.out.println("Static data loaded into the database.");
    }

}
