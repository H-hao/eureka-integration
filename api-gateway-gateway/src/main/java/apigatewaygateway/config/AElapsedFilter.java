package apigatewaygateway.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 计算服务访问耗时
 * 只能通过 java 代码的方式注入到对应的 router 中
 * Created by huanghao on 2019/6/3 0003.
 *
 * @author huanghao
 */
public class AElapsedFilter implements GatewayFilter, Ordered {
    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // chain.filter(exchange)之前的就是 “pre” 部分
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange)
                // 之后的也就是then里边的是 “post” 部分
                .then(Mono.fromRunnable(() -> {
                            Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                            if (startTime != null) {
                                log.info(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                            }
                        })
                );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
