package wang.hijack.mfe.db.model.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Jack
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingBase implements Serializable {
    private static final long serialVersionUID = 7523537583156142973L;
    @CreatedDate
    private long createTime;
    @LastModifiedDate
    private long updateTime;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
