package wang.hijack.mfe.gateway.security.controller;

import wang.hijack.mfe.gateway.security.jwt.JwtTokenProvider;
import wang.hijack.mfe.gateway.security.model.LoginPassword;
import wang.hijack.mfe.gateway.security.model.LoginUser;
import wang.hijack.mfe.gateway.security.model.LoginVo;
import wang.hijack.mfe.gateway.security.model.OnlineUser;
import wang.hijack.mfe.gateway.security.model.annotation.Anonymous;
import wang.hijack.mfe.gateway.security.model.annotation.Authenticated;
import wang.hijack.mfe.gateway.security.model.exception.PasswordException;
import wang.hijack.mfe.gateway.security.service.AuthUserService;
import wang.hijack.mfe.gateway.security.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jack
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenProvider provider;


    @Validated
    @Anonymous
    @PostMapping("login")
    public LoginVo login(@RequestBody LoginUser user) {
        Authentication authentication = authUserService.authenticate(user);
        String token = jwtTokenProvider.createToken(authentication);
        OnlineUser onlineUser = onlineUserService.save(token, authentication.getPrincipal(), request);
        return new LoginVo(token, onlineUser.getNickname(), onlineUser.getAvatar());
    }

    @Authenticated
    @PostMapping("logout")
    public String logout() {
        onlineUserService.logout(provider.resolveToken(request));
        return "success";
    }

    @Authenticated
    @PostMapping("password")
    public void password(@RequestBody LoginPassword password) throws PasswordException {
        authUserService.changePassword(password);
    }
}
