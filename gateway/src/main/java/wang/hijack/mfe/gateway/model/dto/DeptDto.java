package wang.hijack.mfe.gateway.model.dto;

import wang.hijack.mfe.gateway.model.entity.DeptEntity;
import lombok.Builder;

/**
 * @author Jack
 */
@Builder
public class DeptDto {
    private Long id;
    private int level;
    private String orgNo;
    private String orgName;

    public DeptEntity toEntity() {
        DeptEntity entity = new DeptEntity();
        entity.setLevel(level);
        entity.setOrgNo(orgNo);
        entity.setOrgName(orgName);
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
