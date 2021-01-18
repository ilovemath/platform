package wang.hijack.mfe.db.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import wang.hijack.mfe.db.model.entity.DeptEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeptVo {
    private Long id;
    private String name;
    private String deptNo;
    private List<DeptVo> children = new ArrayList<>();

    public DeptVo() {
    }

    public DeptVo(DeptEntity entity) {
        id = entity.getDeptId();
        name = entity.getDeptName();
        deptNo = entity.getDeptNo();
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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
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
