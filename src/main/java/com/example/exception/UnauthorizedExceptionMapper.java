package com.example.exception;

import com.example.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException ex) {
        int statusCode = Response.Status.UNAUTHORIZED.getStatusCode();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), statusCode);
        return Response.status(statusCode)
                .entity(errorMessage)
                .build();
    }
}
