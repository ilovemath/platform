package wang.hijack.mfe.gateway.security.rbac;

import wang.hijack.mfe.gateway.security.model.annotation.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jack
 */
@Component
public class PublicVoter implements AccessDecisionVoter<Object> {
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    public Set<String> authenticatedUrls() {
        Set<String> authenticatedUrls = new HashSet<>();
        requestMappingHandlerMapping.getHandlerMethods().forEach((info, method) -> {
            Authenticated authenticated = method.getMethodAnnotation(Authenticated.class);
            if (authenticated != null) {
                authenticatedUrls.addAll(info.getPatternsCondition().getPatterns());
            }
        });
        return authenticatedUrls;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            for (ConfigAttribute attribute : attributes) {
                if ("permitAll".equals(attribute.toString())) {
                    return ACCESS_GRANTED;
                }
            }
            return ACCESS_DENIED;
        }
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (String path : authenticatedUrls()) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(path);
            if (matcher.matches(request)) {
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_DENIED;
    }

}
