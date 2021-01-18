package wang.hijack.mfe.gateway.security.model;

import javax.validation.constraints.NotBlank;

/**
 * @author Jack
 */
public class LoginUser {
    @NotBlank
    private String orgNo;
    @NotBlank
    private String userNo;
    @NotBlank
    private String code;
    @NotBlank
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
