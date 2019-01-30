package com.example.exception;

import com.example.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException ex) {
        int statusCode = Response.Status.NOT_FOUND.getStatusCode();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), statusCode);
        return Response.status(statusCode)
                .entity(errorMessage)
                .build();
    }
}
