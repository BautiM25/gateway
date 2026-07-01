package ar.edu.unnoba.pdyc2026.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruta para el Microservicio de Usuarios y Social
                .route("user-service", r -> r.path("/api/auth/**", "/api/me/**", "/api/users/**")
                        .filters(f -> f
                                .stripPrefix(1) // Quita el "/api"
                                .tokenRelay()   // Pasa el token JWT de Keycloak al microservicio
                        )
                        .uri("lb://USER-SERVICE"))

                // Ruta para el Microservicio de Notificaciones
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .tokenRelay()
                        )
                        .uri("lb://NOTIFICATION-SERVICE"))

                // Ruta para el Microservicio de Catálogo y Eventos
                .route("catalog-event-service", r -> r.path("/api/events/**", "/api/artists/**", "/api/admin/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .tokenRelay()
                        )
                        .uri("lb://CATALOG-EVENT-SERVICE"))
                .build();
    }
}