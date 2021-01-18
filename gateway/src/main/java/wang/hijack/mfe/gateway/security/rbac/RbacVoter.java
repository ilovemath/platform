package wang.hijack.mfe.gateway.security.rbac;

import wang.hijack.mfe.gateway.service.RoleService;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Jack
 */
@Component
public class RbacVoter implements AccessDecisionVoter<Object> {
    @Autowired
    private RoleService roleService;

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
        int result = ACCESS_ABSTAIN;
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            String roleStr = ga.getAuthority();
            if (StringUtils.isNumeric(roleStr)) {
                result = ACCESS_DENIED;
                Long roleId = Long.valueOf(roleStr);
                for (var app : roleService.getAppsByRoleId(roleId)) {
                    String pattern = "/" + app.getPath() + "/**";
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(pattern);
                    if (matcher.matches(request)) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }

}
