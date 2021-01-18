package wang.hijack.mfe.gateway.model.dto;

import wang.hijack.mfe.gateway.model.entity.UserEntity;
import lombok.Builder;

import java.util.Set;

/**
 * @author Jack
 */
@Builder
public class UserDto {
    private Long id;
    private String orgNo;
    private String userNo;
    private String nickname;
    private int sex;
    private String avatar;
    private String phone;
    private String email;
    private String address;
    private boolean enabled;
    private boolean locked;
    private Set<Long> roleIds;

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        entity.setSex(sex);
        entity.setOrgNo(orgNo);
        entity.setUserNo(userNo);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setAvatar(avatar);
        entity.setAddress(address);
        entity.setNickname(nickname);
        entity.setLocked(locked);
        entity.setEnabled(enabled);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
