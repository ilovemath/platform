package wang.hijack.mfe.gateway.model.entity;

import wang.hijack.mfe.gateway.model.base.AuditingBase;
import wang.hijack.mfe.gateway.model.constants.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.APP)
public class AppEntity extends AuditingBase {
    private static final long serialVersionUID = 3696800244665696670L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;
    private String name;
    @Column(unique = true)
    private String path;
    /**
     * 前端微应用地址
     */
    private String entry;
    /**
     * 后端微服务地址
     */
    private String service;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppEntity that = (AppEntity) o;
        return appId.equals(that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId);
    }
}
