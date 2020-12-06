package gateway;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

public class CafeFilter extends AbstractGatewayFilterFactory<CafeFilter.Config> {

    public static final Logger logger = LogManager.getLogger(UserFilter.class);

    public CafeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(CafeFilter.Config config) {
        return (((exchange, chain) -> {
            logger.info("CafeFilter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPreLogger()) {
                logger.info("CafeFilter start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    logger.info("CafeFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        }));
    }

    @Data
    public static class Config {
        String baseMessage;
        boolean preLogger;
        boolean postLogger;
    }
}
