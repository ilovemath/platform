package wang.hijack.mfe.gateway.model.vo;

import wang.hijack.mfe.gateway.model.entity.AppEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppVo {
    private Long id;
    private String name;
    private String path;
    private String entry;
    private String service;

    public AppVo() {
    }

    public AppVo(AppEntity entity) {
        if (entity == null) {
            return;
        }
        id = entity.getAppId();
        name = entity.getName();
        path = entity.getPath();
        entry = entity.getEntry();
        service = entity.getService();
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
