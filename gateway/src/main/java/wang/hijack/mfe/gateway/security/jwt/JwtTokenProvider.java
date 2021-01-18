package wang.hijack.mfe.gateway.security.jwt;

import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import wang.hijack.mfe.gateway.security.config.Constants;
import wang.hijack.mfe.gateway.security.config.SecurityProperties;
import wang.hijack.mfe.gateway.security.model.OnlineUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
    private Key key;
    private SecurityProperties properties;

    public JwtTokenProvider(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64PrivateKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = System.currentTimeMillis();
        Date validity = new Date(now + properties.getTimeoutSeconds() * 1000);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(Constants.AUTHORITY_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(OnlineUser onlineUser) {
        var authorities = onlineUser.getRoles().stream()
                .map(RoleEntity::getRoleId)
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(onlineUser, null, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(properties.getHeader());
        String tokenStart = properties.getTokenStart();
        if (header != null && header.startsWith(tokenStart)) {
            return header.substring(tokenStart.length());
        }
        return null;
    }

}
