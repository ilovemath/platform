package wang.hijack.mfe.gateway.model.vo;

import wang.hijack.mfe.gateway.model.entity.MenuEntity;
import wang.hijack.mfe.gateway.model.enums.MenuType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo implements Comparable<MenuVo> {
    private Long id;
    private String title;
    private String path;
    private String icon;
    private MenuType type;
    private int sort;
    private List<MenuVo> children = new ArrayList<>();

    public MenuVo() {
    }

    public MenuVo(MenuEntity entity) {
        if (entity == null) {
            return;
        }
        id = entity.getMenuId();
        title = entity.getTitle();
        path = entity.getPath();
        icon = entity.getIcon();
        type = entity.getType();
        sort = entity.getSort();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    public void addChild(MenuVo menuVo) {
        children.add(menuVo);
    }

    @Override
    public int compareTo(MenuVo o) {
        return Integer.compare(sort, o.sort);
    }
}
