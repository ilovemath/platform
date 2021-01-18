package wang.hijack.mfe.db.model.entity;


import wang.hijack.mfe.db.model.base.AuditingBase;
import wang.hijack.mfe.db.model.constants.TableName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jack
 */
@Entity
@Table(name = TableName.DEPT)
public class DeptEntity extends AuditingBase {
    private static final long serialVersionUID = 3424163461211557314L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;
    private String deptNo;
    private String deptName;
    private int level;

    public DeptEntity() {
    }

    public DeptEntity(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
