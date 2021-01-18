package wang.hijack.mfe.db.model.entity;


import wang.hijack.mfe.db.model.base.AuditingBase;
import wang.hijack.mfe.db.model.constants.TableName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.ROLE)
public class RoleEntity extends AuditingBase {
    private static final long serialVersionUID = 6145453217341730943L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "menuId")})
    private Set<MenuEntity> menus = new HashSet<>();

    public RoleEntity() {
    }

    public RoleEntity(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuEntity> menus) {
        this.menus = menus;
    }

    public void addMenus(MenuEntity menuEntity) {
        menus.add(menuEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleEntity that = (RoleEntity) o;
        return roleId.equals(that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
