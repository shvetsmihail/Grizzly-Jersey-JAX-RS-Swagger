package com.example.service;

import com.example.exception.DataNotAcceptException;
import com.example.exception.DataNotFoundException;
import com.example.model.Customer;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class CustomerService {
    private Map<Long, Customer> map;

    public CustomerService() {
        init();
    }

    public List<Customer> getAll() throws DataNotFoundException {
        if (map == null || map.isEmpty()) {
            throw new DataNotFoundException("No customers was found");
        }
        return new ArrayList<>(map.values());
    }

    public Customer get(long id) throws DataNotFoundException {
        Customer customer = map.get(id);
        if (customer == null) {
            throw new DataNotFoundException(String.format("No customer with id = %d as found", id));
        }
        return customer;
    }

    public void add(Customer customer) throws DataNotAcceptException {
        if (map.containsKey(customer.getId())) {
            throw new DataNotAcceptException(String.format("Customer with id = %d already exist", customer.getId()));
        }
        map.put(customer.getId(), customer);
    }

    public void delete(long id) throws DataNotFoundException {
        if (!map.containsKey(id)) {
            throw new DataNotFoundException(String.format("No customer with id = %d was found", id));
        }
        map.remove(id);
    }

    private void init() {
        map = new ConcurrentHashMap<>();

        Customer c1 = new Customer(100, "George", LocalDate.parse("1980-10-01"));
        Customer c2 = new Customer(101, "Thomas", LocalDate.parse("1980-10-01"));
        Customer c3 = new Customer(102, "James", LocalDate.parse("1980-10-01"));
        Customer c4 = new Customer(103, "Alex", LocalDate.parse("1980-10-01"));

        map.put(c1.getId(), c1);
        map.put(c2.getId(), c2);
        map.put(c3.getId(), c3);
        map.put(c4.getId(), c4);
    }
}