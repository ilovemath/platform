package wang.hijack.mfe.db.model.entity;


import wang.hijack.mfe.db.model.base.AuditingBase;
import wang.hijack.mfe.db.model.constants.TableName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.USER)
public class UserEntity extends AuditingBase {
    private static final long serialVersionUID = 4639606820240802831L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String nickname;
    /**
     * 用户性别
     * 0: 女
     * 1: 男
     */
    private int sex;
    /**
     * 用户头像
     */
    private String avatar;
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
     * 部门
     */
    @ManyToOne
    @JoinColumn(name = "deptId")
    private DeptEntity dept;
    /**
     * 角色
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * 账号是否禁用
     */
    private boolean enabled = true;

    /**
     * 账号是否锁定
     */
    private boolean locked = false;

    public UserEntity() {
    }

    public UserEntity(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DeptEntity getDept() {
        return dept;
    }

    public void setDept(DeptEntity dept) {
        this.dept = dept;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRoles(RoleEntity roleEntity) {
        roles.add(roleEntity);
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
}
