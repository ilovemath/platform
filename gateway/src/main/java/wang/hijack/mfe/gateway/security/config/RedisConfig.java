package wang.hijack.mfe.gateway.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author Jack
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.abchina.ebom.gateway.security.cn.ilovex.db.repository")
public class RedisConfig {
}
