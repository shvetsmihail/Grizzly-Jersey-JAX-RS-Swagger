package com.example;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        String protocol = "http://";
        String host = System.getProperty("host", "localhost");
        String port = System.getProperty("port", "8080");
        String basePath = "/myapp/";
        String swaggerPath = "/docs/";

        String uri = protocol + host + ":" + port;
        String baseUri = uri + basePath;

        String[] packagesToScan = {"com.example.controller", "com.example.exception"};

        // create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig resourceConfig = new ResourceConfig()
                .packages(packagesToScan)
                .register(OpenApiResource.class)
                .register(new ApplicationBinder());

        // create and start a new instance of grizzly http server
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), resourceConfig);
        server.start();
        System.out.println("Jersey app started with WADL available at " + baseUri + "application.wadl");

        configureSwagger(baseUri, packagesToScan);
        System.out.println("Configure openApi documentation");

        addSwaggerUIMapping(server, swaggerPath);
        System.out.println("Swagger openApi available at " + uri + swaggerPath);

        System.out.println("Hit enter to stop it...");
        System.in.read();
        server.shutdown();
        System.exit(0);
    }


    private static void configureSwagger(String serverUri, String... packagesToScan) {
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("RESTful Web Servicese")
                .description("RESTful Web Services example using Jersey + JAX RS, Grizzly Http server, Swagger")
                .termsOfService("http://google.com")
                .contact(new Contact().email("example@mail.com"))
                .license(new License().name("License").url("http://google.com"));

        List<Server> servers = new ArrayList<>();
        Server s = new Server();
        s.setUrl(serverUri);
        servers.add(s);

        oas.info(info);
        oas.servers(servers);

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of(packagesToScan).collect(Collectors.toSet()));

        try {
            new JaxrsOpenApiContextBuilder()
                    .openApiConfiguration(oasConfig)
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void addSwaggerUIMapping(HttpServer server, String path) {
        ClassLoader loader = Main.class.getClassLoader();
        CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swagger/");
        docsHandler.setFileCacheEnabled(false);

        ServerConfiguration cfg = server.getServerConfiguration();
        cfg.addHttpHandler(docsHandler, path);
    }
}