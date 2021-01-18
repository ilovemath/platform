package wang.hijack.mfe.gateway.security.config;

import wang.hijack.mfe.gateway.security.jwt.JwtAccessDeniedHandler;
import wang.hijack.mfe.gateway.security.jwt.JwtAuthenticationEntryPoint;
import wang.hijack.mfe.gateway.security.jwt.JwtTokenConfigurer;
import wang.hijack.mfe.gateway.security.rbac.PublicVoter;
import wang.hijack.mfe.gateway.security.rbac.RbacVoter;
import wang.hijack.mfe.gateway.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * @author Jack
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RbacVoter rbacVoter;
    @Autowired
    private PublicVoter publicVoter;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                //禁用csrf,设置cors过滤器
                .csrf()
                .disable()
                .cors()
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 静态资源等等
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/webSocket/**")
                .permitAll()
                // swagger 文档
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs").permitAll()
                // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
                .antMatchers(anonymousUrls()).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                .and()
                //设置异常处理器
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                //配置jwt filter
                .apply(new JwtTokenConfigurer());
    }

    private String[] anonymousUrls() {
        return RequestUtils.getAnonymousUrls(applicationContext);
    }

    private AccessDecisionManager accessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(rbacVoter, publicVoter));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
