package wang.hijack.mfe.gateway.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {
    private String header;
    private String tokenStart;
    private String base64PrivateKey;
    private Long timeoutSeconds;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenStart() {
        return tokenStart;
    }

    public void setTokenStart(String tokenStart) {
        this.tokenStart = tokenStart + " ";
    }

    public String getBase64PrivateKey() {
        return base64PrivateKey;
    }

    public void setBase64PrivateKey(String base64PrivateKey) {
        this.base64PrivateKey = base64PrivateKey;
    }

    public Long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

}
