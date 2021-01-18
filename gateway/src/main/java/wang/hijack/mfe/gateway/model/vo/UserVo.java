package wang.hijack.mfe.gateway.model.vo;

import wang.hijack.mfe.gateway.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserVo {
    private Long id;
    private int sex;
    private String orgNo;
    private String userNo;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String address;
    private boolean enabled;
    private boolean locked;
    private long createTime;
    private long updateTime;
    private List<RoleVo> roles = new ArrayList<>();

    public UserVo() {
    }

    public UserVo(UserEntity entity) {
        id = entity.getUserId();
        userNo = entity.getUserNo();
        orgNo = entity.getOrgNo();
        nickname = entity.getNickname();
        sex = entity.getSex();
        phone = entity.getPhone();
        email = entity.getEmail();
        avatar=entity.getAvatar();
        address = entity.getAddress();
        enabled = entity.isEnabled();
        locked = entity.isLocked();
        createTime = entity.getCreateTime();
        updateTime = entity.getUpdateTime();
        roles.addAll(entity.getRoles().stream().map(RoleVo::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public List<RoleVo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVo> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
