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
    public void add(long id, Customer customer) {
        map.put(id, customer);
    }

    @Override
    public void update(long id, Customer customer) {
        add(id, customer);
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }

    private void init() {
        map = new ConcurrentHashMap<>();

        Customer c2 = new Customer("Anna", LocalDate.parse("1985-02-14"), "female");
        Customer c3 = new Customer("James", LocalDate.parse("1990-05-23"), "male");
        Customer c4 = new Customer("Maria", LocalDate.parse("1995-07-08"), "female");
        Customer c1 = new Customer("George", LocalDate.parse("1980-10-01"), "male");

        map.put(101L, c1);
        map.put(102L, c2);
        map.put(103L, c3);
        map.put(104L, c4);
    }
}
