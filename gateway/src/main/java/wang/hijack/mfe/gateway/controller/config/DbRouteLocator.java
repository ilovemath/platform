package wang.hijack.mfe.gateway.controller.config;

import wang.hijack.mfe.gateway.model.vo.AppVo;
import wang.hijack.mfe.gateway.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 */
@Slf4j
@Component
@EnableConfigurationProperties({ZuulProperties.class})
public class DbRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
    private AppService service;

    public DbRouteLocator(ServerProperties server, ZuulProperties properties, AppService service) {
        super(server.getServlet().getContextPath(), properties);
        this.service = service;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDb());
        return routesMap;
    }

    private Map<String, ZuulRoute> locateRoutesFromDb() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        List<AppVo> apps = service.listAll();
        for (AppVo app : apps) {
            String path = "/" + app.getPath() + "/**";
            String url = app.getService();
            ZuulRoute zuulRoute = new ZuulRoute(path, url);
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
