package gateway;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

public class UserFilter extends AbstractGatewayFilterFactory<UserFilter.Config> {

    public static final Logger logger = LogManager.getLogger(UserFilter.class);

    public UserFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            logger.info("UserFilter baseMessage>>>>>>" + config.getBaseMessage());
            if(config.isPreLogger()) {
                logger.info("UserFilter start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    logger.info("UserFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        }));
    }

    @Data
    public static class Config{
        String baseMessage;
        boolean preLogger;
        boolean postLogger;
    }
}
