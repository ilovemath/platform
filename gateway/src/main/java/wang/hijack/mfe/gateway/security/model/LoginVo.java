package wang.hijack.mfe.gateway.security.model;

/**
 * @author Jack
 */
public class LoginVo {
    private String token;
    private String user;
    private String avatar;

    public LoginVo() {
    }

    public LoginVo(String token, String user, String avatar) {
        this.token = token;
        this.user = user;
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
