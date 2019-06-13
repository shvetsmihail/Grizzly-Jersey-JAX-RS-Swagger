package com.example;

import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class.getName());


    public static void main(String[] args) throws InterruptedException {
        String protocol = "http://";
        String host = System.getProperty("host", "localhost");
        String port = System.getProperty("port", "8080");
        String appContext = "/myapp/";

        String baseUri = protocol + host + ":" + port + appContext;
        HttpServer server = startServer(baseUri);
        LOG.info("monitor.mail.service app started at {}:{}", host, port);

        String swaggerRelativeUrl = appContext + "docs";
        addSwaggerUIMapping(server, swaggerRelativeUrl);

        Thread.currentThread().join();
    }

    private static HttpServer startServer(String appUrl) {
        String[] packagesToScan = {"com.example"};
        // create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig resourceConfig = new ResourceConfig()
                .packages(packagesToScan)
//                .register(OpenApiResource.class)
                .register(ApplicationBinder.builder().build());

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(appUrl), resourceConfig);
    }

    private static void addSwaggerUIMapping(HttpServer server, String path) {
        ClassLoader loader = App.class.getClassLoader();
        CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swagger/");
        docsHandler.setFileCacheEnabled(false);

        HttpHandlerRegistration maping = HttpHandlerRegistration.builder()
                .contextPath(path)
                .urlPattern("")
                .build();

        ServerConfiguration cfg = server.getServerConfiguration();
        cfg.addHttpHandler(docsHandler, maping);

        NetworkListener grizzly = server.getListener("grizzly");
        String serverUrl = "http://" + grizzly.getHost() + ":" + grizzly.getPort();

        LOG.info("Swagger openApi available at {}", serverUrl + path + "/");
    }
}