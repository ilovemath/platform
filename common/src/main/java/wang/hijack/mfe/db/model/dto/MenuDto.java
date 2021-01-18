package wang.hijack.mfe.db.model.dto;

import wang.hijack.mfe.db.model.entity.MenuEntity;
import wang.hijack.mfe.db.model.enums.MenuType;
import lombok.Builder;

/**
 * @author Jack
 */
@Builder
public class MenuDto {
    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 菜单类型
     */
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
     * 应用ID
     */
    private Long appId;

    public MenuEntity toEntity(){
        MenuEntity entity=new MenuEntity();
        entity.setType(type);
        entity.setPath(path);
        entity.setIcon(icon);
        entity.setSort(sort);
        entity.setTitle(title);
        entity.setParentId(parentId);
        entity.setPermission(permission);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
