package com.example.exception;

import com.example.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), statusCode);
        return Response.status(statusCode)
                .entity(errorMessage)
                .build();
    }
}
