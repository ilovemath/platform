package wang.hijack.mfe.gateway.model.dto;

import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import lombok.Builder;

import java.util.Set;

/**
 * @author Jack
 */
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private String desc;
    private Set<Long> menuIds;

    public RoleEntity toEntity() {
        RoleEntity entity = new RoleEntity();
        entity.setName(name);
        entity.setDescription(desc);
        return entity;
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

    public Set<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Set<Long> menuIds) {
        this.menuIds = menuIds;
    }
}
