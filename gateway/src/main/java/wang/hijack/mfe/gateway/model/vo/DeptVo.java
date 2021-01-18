package wang.hijack.mfe.gateway.model.vo;

import wang.hijack.mfe.gateway.model.entity.DeptEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeptVo {
    private Long id;
    private String name;
    private String orgNo;
    private List<DeptVo> children = new ArrayList<>();

    public DeptVo() {
    }

    public DeptVo(DeptEntity entity) {
        id = entity.getOrgId();
        name = entity.getOrgName();
        orgNo = entity.getOrgNo();
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

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public List<DeptVo> getChildren() {
        return children;
    }

    public void setChildren(List<DeptVo> children) {
        this.children = children;
    }

    public void addChild(DeptVo child) {
        this.children.add(child);
    }
}
