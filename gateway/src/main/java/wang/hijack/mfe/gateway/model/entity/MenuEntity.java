package wang.hijack.mfe.gateway.model.entity;

import wang.hijack.mfe.gateway.model.base.AuditingBase;
import wang.hijack.mfe.gateway.model.constants.TableName;
import wang.hijack.mfe.gateway.model.enums.MenuType;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.MENU)
public class MenuEntity extends AuditingBase {
    private static final long serialVersionUID = -1500802433443705436L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    private Long parentId;
    /**
     * 菜单类型
     */
    @Enumerated(EnumType.STRING)
    private MenuType type;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单顺序,数字越小越靠前
     */
    private int sort;
    //以下字段，type=MENU时生效
    /**
     * 路由或外链
     */
    private String path;
    /**
     * 权限标识
     */
    private String permission;

    /**
     * 应用
     */
    @ManyToOne
    @JoinColumn(name = "appId")
    private AppEntity app;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public AppEntity getApp() {
        return app;
    }

    public void setApp(AppEntity app) {
        this.app = app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuEntity that = (MenuEntity) o;
        return Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
