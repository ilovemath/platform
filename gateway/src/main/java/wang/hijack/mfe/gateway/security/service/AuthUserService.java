package wang.hijack.mfe.gateway.security.service;

import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import wang.hijack.mfe.gateway.model.entity.UserEntity;
import wang.hijack.mfe.gateway.repository.UserRepository;
import wang.hijack.mfe.gateway.security.model.LoginPassword;
import wang.hijack.mfe.gateway.security.model.LoginUser;
import wang.hijack.mfe.gateway.security.model.exception.PasswordException;
import wang.hijack.mfe.gateway.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jack
 */
@Slf4j
@Service
public class AuthUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public Authentication authenticate(LoginUser loginUser) throws AuthenticationException {
        UserEntity userEntity = userRepository.findByOrgNoAndUserNo(loginUser.getOrgNo(), loginUser.getUserNo());
        checkUserStatus(userEntity);
        checkCredential(userEntity, loginUser);
        var authorities = getAuthorities(userEntity);
        return new UsernamePasswordAuthenticationToken(userEntity, loginUser.getPassword(), authorities);
    }

    public void changePassword(LoginPassword password) throws PasswordException {
        if (Objects.equals(password.getNewPass(), password.getOldPass())) {
            throw PasswordException.duplicate();
        }
        if (password.getNewPass() == null) {
            throw PasswordException.illegal();
        }
        String no = Objects.requireNonNull(UserHolder.user()).getUserNo();
        UserEntity user = userRepository.findByUserNo(no);
        if (passwordEncoder.matches(password.getOldPass(), user.getPassword())) {
            throw PasswordException.invalid();
        }
        user.setPassword(passwordEncoder.encode(password.getNewPass()));
        userRepository.save(user);
    }

    private Set<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity role : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(role.getRoleId())));
        }
        return authorities;
    }

    private void checkUserStatus(UserEntity userEntity) throws AuthenticationException {
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!userEntity.isEnabled()) {
            throw new DisabledException("用户已禁用");
        }
        if (userEntity.isLocked()) {
            throw new LockedException("用户已被锁定");
        }
    }

    private void checkCredential(UserEntity userEntity, LoginUser loginUser) throws BadCredentialsException {
        if (!passwordEncoder.matches(loginUser.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("用户或密码不正确");
        }
        String token = tokenService.getToken(userEntity.getUserNo());
        if (!Objects.equals(token, loginUser.getCode())) {
            throw new BadCredentialsException("K令不匹配");
        }
    }

}
