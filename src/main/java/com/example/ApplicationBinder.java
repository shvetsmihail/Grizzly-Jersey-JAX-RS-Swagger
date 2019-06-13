package com.example;

import com.example.repository.CustomerRepository;
import com.example.repository.CustomerRepositoryMapImpl;
import com.example.service.CustomerService;
import lombok.Builder;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

@Builder
public class ApplicationBinder extends AbstractBinder {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @Override
    protected void configure() {
        if (customerService != null) {
            bind(customerService).to(CustomerService.class).in(Singleton.class);
        } else {
            bindAsContract(CustomerService.class).in(Singleton.class);
        }

        if (customerRepository != null) {
            bind(customerRepository).to(CustomerRepository.class).in(Singleton.class);
        } else {
            bind(CustomerRepositoryMapImpl.class).to(CustomerRepository.class).in(Singleton.class);
        }
    }
}
