package br.com.emerson.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

@Configuration
//@Profile("test")
public class EmbeddedRedisConfiguration {

    private final static Logger log = LoggerFactory.getLogger(EmbeddedRedisConfiguration.class);

    @Value("${redis.port}")
    private int port;

    @Bean
    public RedisServerBean redisServer() {
        return new RedisServerBean();
    }

    class RedisServerBean implements InitializingBean, DisposableBean {

        private RedisServer redisServer;

        @Override
        public void afterPropertiesSet() throws Exception {
            redisServer = new RedisServer(port);
            redisServer.start();
            log.info("embedded redis server started at port {}", port);
        }

        @Override
        public void destroy() throws Exception {
            if (redisServer != null) {
                redisServer.stop();
                log.info("embedded redis server stopped");
            }
        }
    }
}