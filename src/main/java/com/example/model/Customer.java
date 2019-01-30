package com.example.model;

import com.example.LocalDateAdapter;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Schema(name = "Customer", description = "this is example of Customer object")
public class Customer {
    private long id;
    private String name;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Schema(implementation = String.class, description = "date in format 'yyyy-MM-dd'")
    private LocalDate birthday;

    public Customer(long id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}