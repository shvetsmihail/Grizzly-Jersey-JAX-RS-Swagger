package com.example.repository;

import com.example.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll();

    Customer get(long id);

    void add(Customer customer);

    void delete(long id);
}
