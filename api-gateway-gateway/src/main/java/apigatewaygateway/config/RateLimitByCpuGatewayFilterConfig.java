package apigatewaygateway.config;

import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huanghao on 2019/6/11 0011.
 *
 * @author huanghao
 */
@Configuration
public class RateLimitByCpuGatewayFilterConfig {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder, RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter) {
        return builder.routes()
                .route(r -> r.path("/api/cpu/**")
                        .filters(f -> f.stripPrefix(2)
                                .filter(rateLimitByCpuGatewayFilter))
                        .uri("lb://EUREKA-CONSUMER-FEIGN")
                        .order(0)
                        .id("limit_by_cpu_customer_service")
                )
                .build();
    }

    @Bean
    public RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter(MetricsEndpoint metricsEndpoint) {
        return new RateLimitByCpuGatewayFilter(metricsEndpoint);
    }

}
