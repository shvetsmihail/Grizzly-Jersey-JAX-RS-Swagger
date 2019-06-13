package com.example.repository;

import com.example.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepositoryMapImpl implements CustomerRepository {
    private Map<Long, Customer> map;

    public CustomerRepositoryMapImpl() {
        init();
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Customer get(long id) {
        return map.get(id);
    }

    @Override
    public void add(Customer customer) {
        map.put(customer.getId(), customer);
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }

    private void init() {
        map = new ConcurrentHashMap<>();

        Customer c2 = new Customer(101, "Anna", LocalDate.parse("1985-02-14"), "female");
        Customer c3 = new Customer(102, "James", LocalDate.parse("1990-05-23"), "male");
        Customer c4 = new Customer(103, "Maria", LocalDate.parse("1995-07-08"), "female");
        Customer c1 = new Customer(104, "George", LocalDate.parse("1980-10-01"), "male");

        map.put(c1.getId(), c1);
        map.put(c2.getId(), c2);
        map.put(c3.getId(), c3);
        map.put(c4.getId(), c4);
    }
}
