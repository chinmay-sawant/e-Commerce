package com.learn.ecom.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.learn.ecom.models.Customer;
import com.learn.ecom.models.dto.CustomerReqDTO;
import com.learn.ecom.models.dto.CustomerResDTO;
import com.learn.ecom.services.serviceImpl.CustomerServiceImpl;
import com.learn.ecom.utils.utils;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    
    @MockitoBean
    private CustomerServiceImpl customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation
    public void testGetAllCustomers() throws Exception {
        // Arrange
        List<Customer> customers = List.of(
            new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890"),
            new Customer(2L, "janesmith", "jane.smith@example.com", "password456", "Jane", "Smith", "456 Elm St", "0987654321")
        );
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()").value(customers.size()))
            .andExpect(jsonPath("$[0].id").value(customers.get(0).getId()))
            .andExpect(jsonPath("$[0].firstName").value(customers.get(0).getFirstName()))
            .andExpect(jsonPath("$[0].email").value(customers.get(0).getEmail()))
            .andExpect(jsonPath("$[1].id").value(customers.get(1).getId()))
            .andExpect(jsonPath("$[1].firstName").value(customers.get(1).getFirstName()))
            .andExpect(jsonPath("$[1].email").value(customers.get(1).getEmail()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation

    public void testGetCustomer() throws Exception {
        // Arrange
        List<CustomerResDTO> customers = List.of(
            new CustomerResDTO(1L, "johndoe", "john.doe@example.com", "John", "Doe", "123 Main St", "1234567890"),
            new CustomerResDTO(2L, "janesmith", "jane.smith@example.com", "Jane", "Smith", "456 Elm St", "0987654321")
        );
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(
            new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890"),
            new Customer(2L, "janesmith", "jane.smith@example.com", "password456", "Jane", "Smith", "456 Elm St", "0987654321")
        ));
        try (MockedStatic<utils> mockedUtils = Mockito.mockStatic(utils.class)) {
            mockedUtils.when(() -> utils.convertModelList(Mockito.anyList(), Mockito.eq(CustomerResDTO.class))).thenReturn(customers);
        }

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()").value(customers.size()))
            .andExpect(jsonPath("$[0].id").value(customers.get(0).getId()))
            .andExpect(jsonPath("$[0].firstName").value(customers.get(0).getFirstName()))
            .andExpect(jsonPath("$[0].email").value(customers.get(0).getEmail()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation

    public void testGetCustomerById() throws Exception {
        // Arrange
        Customer customer = new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        CustomerResDTO customerResDTO = new CustomerResDTO(1L, "johndoe", "john.doe@example.com", "John", "Doe", "123 Main St", "1234567890");
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);
        try (MockedStatic<utils> mockedUtils = Mockito.mockStatic(utils.class)) {
            mockedUtils.when(() -> utils.convertModel(customer, CustomerResDTO.class)).thenReturn(customerResDTO);
        }
        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/1")
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(customerResDTO.getId()))
            .andExpect(jsonPath("$.firstName").value(customerResDTO.getFirstName()))
            .andExpect(jsonPath("$.email").value(customerResDTO.getEmail()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).getCustomerById(1L);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation
    public void testPostMethodName() throws Exception {
        // Arrange
        CustomerReqDTO requestDTO = new CustomerReqDTO("johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        Customer customerToCreate = new Customer(null, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        Customer createdCustomer = new Customer(1L, "johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        CustomerResDTO responseDTO = new CustomerResDTO(1L, "johndoe", "john.doe@example.com", "John", "Doe", "123 Main St", "1234567890");
        
        try (MockedStatic<utils> mockedUtils = Mockito.mockStatic(utils.class)) {
            mockedUtils.when(() -> utils.convertModel(requestDTO, Customer.class)).thenReturn(customerToCreate);
            mockedUtils.when(() -> utils.convertModel(createdCustomer, CustomerResDTO.class)).thenReturn(responseDTO);
        }
        
        Mockito.when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(createdCustomer);

        // Act & Assert
        mockMvc.perform(post("/api/v1/customers")
                    .with(csrf()) // Add CSRF token

                .contentType("application/json")
                .content("{\"username\":\"johndoe\",\"email\":\"john.doe@example.com\",\"password\":\"password123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"phone\":\"1234567890\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(responseDTO.getId()))
            .andExpect(jsonPath("$.username").value(responseDTO.getUsername()))
            .andExpect(jsonPath("$.email").value(responseDTO.getEmail()))
            .andExpect(jsonPath("$.firstName").value(responseDTO.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(responseDTO.getLastName()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).createCustomer(Mockito.any(Customer.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation
    public void testPutMethodNameWithFullDetails() throws Exception {
        // Arrange
        CustomerReqDTO requestDTO = new CustomerReqDTO("johndoe_updated", "john.updated@example.com", "newpassword123", "Johnny", "Doey", "456 Oak St", "9876543210");
        Customer customerToUpdate = new Customer(null, "johndoe_updated", "john.updated@example.com", "newpassword123", "Johnny", "Doey", "456 Oak St", "9876543210");
        Customer updatedCustomer = new Customer(1L, "johndoe_updated", "john.updated@example.com", "newpassword123", "Johnny", "Doey", "456 Oak St", "9876543210");
        CustomerResDTO responseDTO = new CustomerResDTO(1L, "johndoe_updated", "john.updated@example.com", "Johnny", "Doey", "456 Oak St", "9876543210");
        
        try (MockedStatic<utils> mockedUtils = Mockito.mockStatic(utils.class)) {
            mockedUtils.when(() -> utils.convertModel(requestDTO, Customer.class)).thenReturn(customerToUpdate);
            mockedUtils.when(() -> utils.convertModel(updatedCustomer, CustomerResDTO.class)).thenReturn(responseDTO);
        }
        
        Mockito.when(customerService.updateCustomer(Mockito.eq(1L), Mockito.any(Customer.class))).thenReturn(updatedCustomer);

        // Act & Assert
        mockMvc.perform(put("/api/v1/customers/1")
         .with(csrf())
                .contentType("application/json")
                .content("{\"username\":\"johndoe_updated\",\"email\":\"john.updated@example.com\",\"password\":\"newpassword123\",\"firstName\":\"Johnny\",\"lastName\":\"Doey\",\"address\":\"456 Oak St\",\"phone\":\"9876543210\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(responseDTO.getId()))
            .andExpect(jsonPath("$.username").value(responseDTO.getUsername()))
            .andExpect(jsonPath("$.email").value(responseDTO.getEmail()))
            .andExpect(jsonPath("$.firstName").value(responseDTO.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(responseDTO.getLastName()))
            .andExpect(jsonPath("$.address").value(responseDTO.getAddress()))
            .andExpect(jsonPath("$.phoneNumber").value(responseDTO.getPhoneNumber()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).updateCustomer(Mockito.eq(1L), Mockito.any(Customer.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation
    public void testPatchMethodName() throws Exception {
        // Arrange
        CustomerReqDTO entity = new CustomerReqDTO("johndoe", "john.doe@example.com", "password123", "John", "Doe", "123 Main St", "1234567890");
        Customer dbCustomer = new Customer(1L, "johndoe", "john.doe@example.com", null, "John", "Doe", null, null);
        Customer updatedCustomer = new Customer(1L, "johndoe", "john.doe@example.com", null, "John", "Doe", null, null);
        CustomerResDTO customerResDTO = new CustomerResDTO(1L, "johndoe", "john.doe@example.com", "John", "Doe", "123 Main St", "1234567890");
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(dbCustomer);
    try (MockedStatic<utils> mockedUtils = Mockito.mockStatic(utils.class)) {
        mockedUtils.when(() -> utils.convertModel(Mockito.any(CustomerReqDTO.class), Mockito.eq(Customer.class))).thenReturn(entity);
        mockedUtils.when(() -> utils.convertModel(updatedCustomer, CustomerResDTO.class)).thenReturn(customerResDTO);
    }
    Mockito.when(customerService.updateCustomer(Mockito.eq(1L), Mockito.any(Customer.class))).thenReturn(updatedCustomer);


        // Act & Assert
        mockMvc.perform(patch("/api/v1/customers/1")
        .with(csrf())
                .contentType("application/json")
                .content("{\"firstName\":\"John\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(customerResDTO.getId()))
            .andExpect(jsonPath("$.firstName").value(customerResDTO.getFirstName()))
            .andExpect(jsonPath("$.email").value(customerResDTO.getEmail()));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).getCustomerById(1L);
        Mockito.verify(customerService, Mockito.times(1)).updateCustomer(1L, dbCustomer);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"}) // <--- Add this annotation
    public void testDeleteMethodName() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/v1/customers/1").with(csrf())
        )
            .andExpect(status().isNoContent())
            .andExpect(content().string("Customer with ID 1 deleted successfully."));

        // Verify
        Mockito.verify(customerService, Mockito.times(1)).deleteCustomer(1L);
    }
}
