package wang.hijack.mfe.gateway.model.entity;

import wang.hijack.mfe.gateway.model.base.AuditingBase;
import wang.hijack.mfe.gateway.model.constants.TableName;

import javax.persistence.*;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.CHECK)
public class CheckEntity extends AuditingBase {
    private static final long serialVersionUID = 8680605994984561869L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chkId;
    private Long checkerId;
    private Long applicantId;
    @Lob
    private String entity;
    private Boolean status;

    public Long getChkId() {
        return chkId;
    }

    public void setChkId(Long chkId) {
        this.chkId = chkId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
