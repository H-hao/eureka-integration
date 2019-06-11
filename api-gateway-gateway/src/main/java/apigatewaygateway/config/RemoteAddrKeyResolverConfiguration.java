package apigatewaygateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 使用请求 IP 作为限流键
 *
 * @author huanghao
 */
@Configuration
public class RemoteAddrKeyResolverConfiguration {

    @Bean(RemoteAddrKeyResolver.BEAN_NAME)
    public KeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }

    class RemoteAddrKeyResolver implements KeyResolver {

        public static final String BEAN_NAME = "remoteAddrKeyResolver";

        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {
            return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        }
    }

}