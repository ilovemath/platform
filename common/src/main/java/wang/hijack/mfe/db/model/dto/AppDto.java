package wang.hijack.mfe.db.model.dto;

import wang.hijack.mfe.db.model.entity.AppEntity;
import lombok.Builder;

/**
 * @author Jack
 */
@Builder
public class AppDto {
    private Long id;
    private String name;
    private String path;
    private String entry;
    private String service;

    public AppEntity toEntity() {
        AppEntity entity = new AppEntity();
        entity.setName(name);
        entity.setPath(path);
        entity.setEntry(entry);
        entity.setService(service);
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
