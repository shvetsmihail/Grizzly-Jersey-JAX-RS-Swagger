package com.example.exception;

import com.example.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotAcceptExceptionMapper implements ExceptionMapper<DataNotAcceptException> {
    @Override
    public Response toResponse(DataNotAcceptException ex) {
        int statusCode = Response.Status.NOT_ACCEPTABLE.getStatusCode();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), statusCode);
        return Response.status(statusCode)
                .entity(errorMessage)
                .build();
    }
}
