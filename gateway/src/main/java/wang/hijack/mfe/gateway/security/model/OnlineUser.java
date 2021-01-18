package wang.hijack.mfe.gateway.security.model;

import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import wang.hijack.mfe.gateway.model.entity.UserEntity;
import wang.hijack.mfe.gateway.security.config.Constants;
import wang.hijack.mfe.gateway.util.BeanUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jack
 */
@ToString
@RedisHash(value = Constants.ONLINE_USER, timeToLive = 3600)
public class OnlineUser implements Serializable {
    private static final long serialVersionUID = -9070145989334039984L;
    /**
     * jwt token
     */
    @Id
    @JsonIgnore
    private String token;
    /**
     * 机构号
     */
    private String orgNo;
    /**
     * 用户号
     */
    @Indexed
    private String userNo;
    /**
     * 用户姓名
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 角色
     */
    @JsonIgnore
    private Set<RoleEntity> roles = new HashSet<>();
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 登录时间戳
     */
    private Long loginTime;

    public OnlineUser() {
        loginTime = System.currentTimeMillis();
    }

    public OnlineUser(String jwtToken, UserEntity entity) {
        token = jwtToken;
        from(entity);
        loginTime = System.currentTimeMillis();
    }

    public OnlineUser(String jwtToken, Object user) {
        token = jwtToken;
        if (user instanceof UserEntity) {
            from((UserEntity) user);
        } else {
            BeanUtils.copyProperties(user, this);
        }
        loginTime = System.currentTimeMillis();
    }

    private void from(UserEntity entity) {
        orgNo = entity.getOrgNo();
        userNo = entity.getUserNo();
        nickname = entity.getNickname();
        avatar = entity.getAvatar();
        sex = entity.getSex();
        phone = entity.getPhone();
        email = entity.getEmail();
        address = entity.getAddress();
        roles.addAll(entity.getRoles());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
