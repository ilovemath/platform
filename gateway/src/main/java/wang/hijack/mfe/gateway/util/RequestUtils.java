package wang.hijack.mfe.gateway.util;


import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import wang.hijack.mfe.gateway.security.model.annotation.Anonymous;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jack
 */
public class RequestUtils {
    private static final String UNKNOWN = "unknown";

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    public static String[] getAnonymousUrls(ApplicationContext applicationContext) {
        Set<String> anonymousUrls = new HashSet<>();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        requestMappingHandlerMapping.getHandlerMethods().forEach((info, method) -> {
            Anonymous anonymous = method.getMethodAnnotation(Anonymous.class);
            if (anonymous != null) {
                anonymousUrls.addAll(info.getPatternsCondition().getPatterns());
            }
        });
        return anonymousUrls.toArray(new String[0]);
    }

}
