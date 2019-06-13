package com.example.controller;

import com.example.exception.DataNotAcceptException;
import com.example.exception.DataNotFoundException;
import com.example.model.Customer;
import com.example.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Singleton
@Path("/customers")
@Tag(name = "customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "500", description = "Internal server exception")
public class CustomerResource {
    private CustomerService service;

    @Inject
    public CustomerResource(CustomerService service) {
        this.service = service;
    }

    @GET
    @Path("/all")
    @Operation(summary = "Get customers", description = "Get list of customers")
    @ApiResponse(responseCode = "200", description = "List of customers",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    public Response getAllCustomers() {
        List<Customer> all = service.getAll();
        return Response.ok(all).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get customer by id", description = "Using id getting customer info from database")
    @ApiResponse(responseCode = "200", description = "The Customer",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "404", description = "No customers found")
    public Response getCustomer(@Parameter(description = "Customer id") @PathParam("id") long id) throws DataNotFoundException {
        Customer customer = service.get(id);
        return Response.ok(customer).build();
    }

    @POST
    @Path("/add/{id}")
    @Operation(summary = "Add new customer", description = "Create new customer and put it to database")
    @RequestBody(
            description = "Customer that needs to be added",
            required = true,
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "201", description = "Successfully added")
    @ApiResponse(responseCode = "406", description = "Customer data not accepted")
    public Response addCustomer(@Parameter(description = "New Customer id") @PathParam("id") long id,
                                Customer customer,
                                @Context UriInfo uriInfo) throws DataNotAcceptException {
        service.add(id, customer);
        URI uri = uriInfo.getBaseUriBuilder()
                .path(this.getClass())
                .path(String.valueOf(id))
                .build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/update/{id}")
    @Operation(summary = "Update existed customer")
    @RequestBody(
            description = "Customer that needs to be updated",
            required = true,
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @ApiResponse(responseCode = "406", description = "Customer data not accepted")
    public Response updateCustomer(@Parameter(description = "Customer id") @PathParam("id") long id,
                                   Customer customer,
                                   @Context UriInfo uriInfo) throws DataNotAcceptException {
        service.update(id, customer);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", description = "The Customer was delete successfully")
    @ApiResponse(responseCode = "404", description = "No customers found")
    public Response deleteCustomer(@PathParam("id") long id) throws DataNotFoundException {
        service.delete(id);
        return Response.ok().build();
    }
}