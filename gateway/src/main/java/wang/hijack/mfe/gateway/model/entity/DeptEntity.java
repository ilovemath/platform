package wang.hijack.mfe.gateway.model.entity;

import wang.hijack.mfe.gateway.model.base.AuditingBase;
import wang.hijack.mfe.gateway.model.constants.TableName;

import javax.persistence.*;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.DEPT)
public class DeptEntity extends AuditingBase {
    private static final long serialVersionUID = 3424163461211557314L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;
    private int level;
    private String orgNo;
    private String orgName;

    public DeptEntity() {
    }

    public DeptEntity(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
