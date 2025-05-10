package com.learn.ecom.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.ecom.models.Customer;
import com.learn.ecom.models.dto.CustomerReqDTO;
import com.learn.ecom.models.dto.CustomerResDTO;
import com.learn.ecom.services.serviceImpl.CustomerServiceImpl;
import com.learn.ecom.utils.utils;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    
    public final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResDTO> getCustomer() {
        return utils.convertModelList(customerService.getAllCustomers(), CustomerResDTO.class);
    }

    @GetMapping("/{id}")
    public CustomerResDTO getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return utils.convertModel(customer, CustomerResDTO.class);
    }

    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)          
    public CustomerResDTO postMethodName(@RequestBody CustomerReqDTO entity) {
        Customer customer = customerService.createCustomer(utils.convertModel(entity, Customer.class));
        return utils.convertModel(customer, CustomerResDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResDTO putMethodName(@RequestBody CustomerReqDTO entity,    
                                @PathVariable Long id) {
        Customer customer = customerService.updateCustomer(id, utils.convertModel(entity, Customer.class));
        return utils.convertModel(customer, CustomerResDTO.class);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResDTO patchMethodName(@RequestBody CustomerReqDTO entity,    
                                @PathVariable Long id) {
        Customer DBCust = customerService.getCustomerById(id);
        utils.updateModel(entity, DBCust);
        Customer customer = customerService.updateCustomer(id, DBCust);
        return utils.convertModel(customer, CustomerResDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteMethodName(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer with ID " + id + " deleted successfully.";
    }
}
