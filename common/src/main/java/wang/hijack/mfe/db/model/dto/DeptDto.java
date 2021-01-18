package wang.hijack.mfe.db.model.dto;

import wang.hijack.mfe.db.model.entity.DeptEntity;
import lombok.Builder;

/**
 * @author Jack
 */
@Builder
public class DeptDto {
    private Long id;
    private int level;
    private String deptNo;
    private String deptName;

    public DeptEntity toEntity() {
        DeptEntity entity = new DeptEntity();
        entity.setLevel(level);
        entity.setDeptNo(deptNo);
        entity.setDeptName(deptName);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
