package com.example.filter;

import com.example.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);
    private static final String DOC_PATH_PATTERN = "openapi";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        UriInfo uriInfo = requestContext.getUriInfo();
        String path = uriInfo.getRequestUri().getPath();

        String token = requestContext.getHeaderString("Authorization");
        LOG.info("AuthorizationFilter: {}, token: {}", path, token);

        if (path.contains(DOC_PATH_PATTERN)) {
            LOG.info("Doc path. Skip auth");
        } else {
            doAuth(token);
        }
    }

    private void doAuth(String token) {
        if (!"Bearer test".equals(token)) {
            throw new UnauthorizedException("Bad token: " + token);
        }
    }
}
