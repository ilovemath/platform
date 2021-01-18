package wang.hijack.mfe.db.model.vo;

import wang.hijack.mfe.db.model.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleVo {
    long createTime;
    private Long id;
    private String name;
    private String desc;
    private List<MenuVo> menus = new ArrayList<>();

    public RoleVo() {
    }

    public RoleVo(RoleEntity entity) {
        if (entity == null) {
            return;
        }
        id = entity.getRoleId();
        name = entity.getName();
        desc = entity.getDescription();
        createTime = entity.getCreateTime();
        menus.addAll(entity.getMenus().stream().map(MenuVo::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<MenuVo> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuVo> menus) {
        this.menus = menus;
    }
}
