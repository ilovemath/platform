package wang.hijack.mfe.gateway.controller.system;

import wang.hijack.mfe.gateway.model.dto.RoleDto;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.RoleVo;
import wang.hijack.mfe.gateway.service.RoleService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/**
 * @author Jack
 */
@RestController
@RequestMapping("system")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("roles")
    public Page<RoleVo> list(@RequestParam(required = false) String name,
                             @RequestParam(required = false) List<Long> createTime,
                             Pageable pageable) {
        return roleService.list(name, createTime, pageable.previousOrFirst());
    }

    @PostMapping("roles")
    public ResponseEntity<RoleVo> insert(@RequestBody RoleDto dto) {
        var role = roleService.insert(dto);
        return ResponseEntity.ok(role);
    }


    @PutMapping("roles/{id}")
    public ResponseEntity<RoleVo> update(@PathVariable Long id, @RequestBody RoleDto dto) throws EntityNotExistException {
        dto.setId(id);
        var role = roleService.update(dto);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("roles")
    public ResponseEntity<String> delete(@RequestParam List<Long> ids) {
        roleService.deleteAll(ids);
        return ResponseEntity.ok("成功");
    }

}
