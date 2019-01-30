package com.example;

import com.example.service.CustomerService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(CustomerService.class);
    }
}
