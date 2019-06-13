package com.example.model;

import com.example.LocalDateAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Customer", description = "this is example of Customer object")
public class Customer {
    @Schema(description = "Customer id", required = true, example = "100")
    private long id;

    @Schema(description = "Customer name", required = true, example = "Bart Simpson")
    private String name;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Schema(implementation = String.class, description = "date in format 'yyyy-MM-dd'",
            required = true, example = "2019-06-13")
    private LocalDate birthday;

    @Schema(description = "Customer sex", required = true, example = "male",
            allowableValues = {"male", "female"})
    private String sex;
}