package apigatewaygateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 修改请求体 全局过滤器
 * 假设 请求体如下
 * <pre>
 * {
 *     "serialNumber": "请求流水号",
 *     "payload" : {
 *         // ... 这里是有效载荷，存放具体的数据
 *     }
 * }
 * </pre>
 *
 * tips：此类主要参考 ModifyRequestBodyGatewayFilterFactory，大部分内容没有变化，除了todo 部分；<br>
 * tips：如果直接使用 ModifyRequestBodyGatewayFilterFactory 类，使用方式则参考
 * {@link RateLimitByCpuGatewayFilterConfig#customerRouteLocator(RouteLocatorBuilder, RateLimitByCpuGatewayFilter)}
 *
 * @author huanghao
 * @see org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory
 */
@Slf4j
@Component
public class ModifyRequestBodyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入 {} 过滤器，执行过滤方法", this.getClass().getName());
        ServerHttpRequest request = exchange.getRequest();
        // 可以修改 请求头、cookies、请求参数 等信息
        // HttpHeaders headers = request.getHeaders();
        // MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        // MultiValueMap<String, String> queryParams = request.getQueryParams();
        // Flux<DataBuffer> body = request.getBody();
        MediaType mediaType = request.getHeaders().getContentType();

        ServerRequest serverRequest = new DefaultServerRequest(exchange);
        // read & modify body

        Mono modifiedBody = serverRequest.bodyToMono(Map.class)
                // .log("modify_request_mono", Level.INFO)
                .flatMap(body -> {
                    if (MediaType.APPLICATION_JSON_UTF8.isCompatibleWith(mediaType)) {
                        // TODO 在这里修改 请求主体，加密，加请求参数等，
                        Map<String, Object> newBodyMap = new HashMap<>(body);
                        newBodyMap.put("requestId", UUID.randomUUID().toString());
                        log.info("修改请求主体：{} -> {}", body, newBodyMap);
                        return Mono.just(newBodyMap);
                    }
                    return Mono.empty();
                });

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, Map.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());

        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        // if the body is changing content types, set it here, to the bodyInserter will know about it
        if (mediaType != null) {
            headers.set(HttpHeaders.CONTENT_TYPE, mediaType.toString());
        }
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage,  new BodyInserterContext())
                // .log("modify_request", Level.INFO)
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                        @Override
                        public HttpHeaders getHeaders() {
                            long contentLength = headers.getContentLength();
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                            }
                            return httpHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    @Override
    public int getOrder() {
        return 0;
    }

}