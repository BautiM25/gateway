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
                // ----------------------------------------------------
                // 1. MICROSERVICIO DE USUARIOS
                // ----------------------------------------------------
                .route("user-service", r -> r.path(
                        "/api/admin/users/**", // AdminUserController
                        "/api/auth/**",        // AuthController
                        "/api/me/**"           // UserActionController
                    )
                        .filters(f -> f
                                .stripPrefix(1)
                                .tokenRelay()
                        )
                        .uri("lb://USER-SERVICE"))

                // ----------------------------------------------------
                // 2. MICROSERVICIO DE NOTIFICACIONES
                // ----------------------------------------------------
                .route("notification-service", r -> r.path(
                        "/api/notifications/**" // NotificationController
                    )
                        .filters(f -> f
                                .stripPrefix(1)
                                .tokenRelay()
                        )
                        .uri("lb://NOTIFICATION-SERVICE"))

                // ----------------------------------------------------
                // 3. MICROSERVICIO DE CATÁLOGO Y EVENTOS
                // ----------------------------------------------------
                .route("catalog-event-service", r -> r.path(
                        "/api/admin/artists/**", // ArtistController
                        "/api/admin/events/**",  // EventController
                        "/api/artists/**",       // PublicArtistController
                        "/api/events/**"         // PublicEventController
                    )
                        .filters(f -> f
                                .stripPrefix(1)
                                .tokenRelay()
                        )
                        .uri("lb://CATALOG-EVENT-SERVICE"))
                .build();
    }
}