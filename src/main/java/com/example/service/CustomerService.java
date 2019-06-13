package com.example.service;

import com.example.exception.DataNotAcceptException;
import com.example.exception.DataNotFoundException;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

import javax.inject.Inject;
import java.util.List;

public class CustomerService {

    private CustomerRepository customerRepository;

    @Inject
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    public Customer get(long id) throws DataNotFoundException {
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            throw new DataNotFoundException(String.format("No customer with id = %d as found", id));
        }
        return customer;
    }

    public void add(long id, Customer customer) throws DataNotAcceptException {
        if (customerRepository.get(id) != null) {
            throw new DataNotAcceptException(String.format("Customer with id = %d already exist", id));
        }
        customerRepository.add(id, customer);
    }

    public void update(long id, Customer customer) throws DataNotAcceptException {
        if (customerRepository.get(id) == null) {
            throw new DataNotAcceptException(String.format("Customer with id = %d was not found", id));
        }
        customerRepository.update(id, customer);
    }

    public void delete(long id) throws DataNotFoundException {
        if (customerRepository.get(id) == null) {
            throw new DataNotFoundException(String.format("No customer with id = %d was not found", id));
        }
        customerRepository.delete(id);
    }
}