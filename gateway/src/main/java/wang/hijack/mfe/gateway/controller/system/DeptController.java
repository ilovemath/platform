package wang.hijack.mfe.gateway.controller.system;

import wang.hijack.mfe.gateway.model.dto.DeptDto;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.DeptVo;
import wang.hijack.mfe.gateway.service.DeptService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Jack
 */
@RestController
@RequestMapping("system")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("depts")
    public List<DeptVo> list() {
        return deptService.listTree();
    }

    @GetMapping("depts/map")
    public Map<String, String> listMap() {
        return deptService.listMap();
    }

    @PostMapping("depts")
    public ResponseEntity<DeptVo> insert(@RequestBody DeptDto dto) {
        var dept = deptService.insert(dto);
        return ResponseEntity.ok(dept);
    }

    @PutMapping("depts/{id}")
    public ResponseEntity<DeptVo> update(@PathVariable Long id, @RequestBody DeptDto dto) throws EntityNotExistException {
        dto.setId(id);
        var dept = deptService.update(dto);
        return ResponseEntity.ok(dept);
    }

    @DeleteMapping("depts")
    public ResponseEntity<String> delete(@RequestParam List<Long> ids) {
        deptService.deleteAll(ids);
        return ResponseEntity.ok("成功");
    }

}
