package com.learn.ecom.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.ecom.models.Customer;
import com.learn.ecom.repository.CustomerRepository;
import com.learn.ecom.services.serviceImpl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ControllerServiceTests {
    
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

     @Test
    public void testGetAllCustomers() {
        // Arrange
        List<Customer> customers = List.of(
            new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890"),
            new Customer(2L, "janesmith", "jane.smith@example.com", "password456", "Jane", "Smith", "456 Elm St", "0987654321")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        Customer customer = new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer(null, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        Customer savedCustomer = new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        when(customerRepository.save(customer)).thenReturn(savedCustomer);

        // Act
        Customer result = customerService.createCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        Customer updatedCustomer = new Customer(1L, "johndoe_updated", "john.updated@example.com", "newpassword123", "Johnny", "Doey", "456 Oak St", "9876543210");
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(updatedCustomer));

        // Act
        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        // Assert
        assertNotNull(result);
        assertEquals("johndoe_updated", result.getUsername());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(updatedCustomer);
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        Long customerId = 1L;
        doNothing().when(customerRepository).deleteById(customerId);

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

}
