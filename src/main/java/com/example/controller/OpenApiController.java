package com.example.controller;

import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.integration.ClasspathOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;


@Path("/openapi.{type:json|yaml}")
@SecurityScheme(name = "Authorization",
        description = "Use 'Bearer sid' token for authorisation. Bearer test",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization")
@OpenAPIDefinition(
        info = @Info(title = "Awesome Grizzly + JAX-RS + Swagger application",
                version = "1.0.0",
                description = "Project is based on JAX-RS technologies with Grizzly Servlet Containers and Swagger Annotations as interactive documentation"),
        security = {@SecurityRequirement(name = "Authorization")}
)
@Singleton
public class OpenApiController extends BaseOpenApiResource {
    @Context
    ServletConfig config;
    @Context
    Application app;

    public OpenApiController() {
        OpenApiConfigurationLoader loader = new ClasspathOpenApiConfigurationLoader();
        try {
            if (loader.exists("schemas.yaml")) {
                openApiConfiguration = loader.load("schemas.yaml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (openApiConfiguration == null) {
            openApiConfiguration = new SwaggerConfiguration()
                    .openAPI(new OpenAPI());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/yaml"})
    @Operation(hidden = true)
    public Response getOpenApi(@Context HttpHeaders headers,
                               @Context UriInfo uriInfo,
                               @PathParam("type") String type) throws Exception {
        URI baseUri = uriInfo.getBaseUri();
        String appUrl = baseUri.toString();
        if (baseUri.getPort() == 80) {
            appUrl = appUrl.replaceFirst(":80", "");
        }

        openApiConfiguration.getOpenAPI()
                .addServersItem(new Server().url(appUrl));

        return super.getOpenApi(headers, config, app, uriInfo, type);
    }
}
