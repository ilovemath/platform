package wang.hijack.mfe.gateway.util;

import wang.hijack.mfe.gateway.model.entity.AppEntity;
import wang.hijack.mfe.gateway.model.entity.MenuEntity;
import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import wang.hijack.mfe.gateway.security.model.OnlineUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
public class UserHolder {
    public static OnlineUser user() {
        try {
            return (OnlineUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Set<RoleEntity> roles() {
        OnlineUser onlineUser = user();
        if (onlineUser != null) {
            return onlineUser.getRoles();
        }
        return new HashSet<>();
    }

    public static Set<MenuEntity> menus() {
        return roles()
                .stream()
                .map(RoleEntity::getMenus)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public static Set<AppEntity> apps() {
        return menus()
                .stream()
                .filter(m -> m.getApp() != null)
                .map(MenuEntity::getApp).collect(Collectors.toSet());
    }
}
