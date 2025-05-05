package com.learn.ecom.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.learn.ecom.models.Customer;

@DataJpaTest // Configures an in-memory database and JPA
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager; // Useful for setting up test data directly

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        // Clear out previous test data (optional, but good practice sometimes)
        // entityManager.clear(); // Or customerRepository.deleteAll();

        // Create test customers
        customer1 = new Customer();
        customer1.setUsername("john_doe");
        customer1.setPassword("password123"); // Assuming Customer has password field
        customer1.setEmail("john.doe@example.com"); // Assuming Customer has email field
        // Set other relevant fields for Customer entity

        customer2 = new Customer();
        customer2.setUsername("jane_doe");
        customer2.setPassword("securepass");
        customer2.setEmail("jane.doe@example.com");
        // Set other relevant fields for Customer entity

        // Persist test data using TestEntityManager
        // Using persist returns the managed entity
        entityManager.persist(customer1);
        entityManager.persist(customer2);

        // Ensure data is written to the database before tests run
        entityManager.flush(); // Forces sync with database
        entityManager.clear(); // Detaches entities from persistence context
    }

    @Test
    void whenFindByUsername_thenReturnCustomer() {
        // When
        Customer foundCustomer = customerRepository.findByUsername(customer1.getUsername());

        // Then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getUsername()).isEqualTo(customer1.getUsername());
        assertThat(foundCustomer.getEmail()).isEqualTo(customer1.getEmail());
        // Assert other relevant fields
    }

    @Test
    void whenFindByUsername_thenReturnNullForNonexistentUsername() {
        // When
        Customer foundCustomer = customerRepository.findByUsername("non_existent_user");

        // Then
        assertThat(foundCustomer).isNull();
    }

    // Optional: Test a basic save operation to ensure setup works
    @Test
    void whenSaveCustomer_thenReturnSavedCustomer() {
        // Given
        Customer newCustomer = new Customer();
        newCustomer.setUsername("test_user");
        newCustomer.setPassword("testpass");
        newCustomer.setEmail("test@example.com");

        // When
        Customer savedCustomer = customerRepository.save(newCustomer);

        // Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull(); // Assuming ID is auto-generated
        assertThat(savedCustomer.getUsername()).isEqualTo("test_user");

        // Verify it exists in the database
        assertThat(customerRepository.findById(savedCustomer.getId())).isPresent();
    }

    // Add tests for other methods inherited from JpaRepository if needed
    // For example: findById, findAll, deleteById, etc.
}