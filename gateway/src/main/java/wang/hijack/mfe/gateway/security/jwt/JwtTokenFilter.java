package wang.hijack.mfe.gateway.security.jwt;

import wang.hijack.mfe.gateway.security.model.OnlineUser;
import wang.hijack.mfe.gateway.security.service.OnlineUserService;
import wang.hijack.mfe.gateway.util.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jack
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtTokenProvider provider = ContextHolder.getBean(JwtTokenProvider.class);
        OnlineUserService service = ContextHolder.getBean(OnlineUserService.class);
        String token = provider.resolveToken(request);
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(token)) {
            log.debug("jwt token is empty, uri: {}", uri);
        } else {
            OnlineUser onlineUser = service.getByToken(token);
            if (onlineUser != null && provider.validateToken(token)) {
                Authentication authentication = provider.getAuthentication(onlineUser);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), uri);
            } else {
                log.debug("no valid JWT token found, uri: {}", uri);
            }
        }
        filterChain.doFilter(request, response);
    }
}
