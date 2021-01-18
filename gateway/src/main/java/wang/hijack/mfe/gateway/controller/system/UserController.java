package wang.hijack.mfe.gateway.controller.system;

import wang.hijack.mfe.gateway.model.dto.UserDto;
import wang.hijack.mfe.gateway.model.exception.EntityNotExistException;
import wang.hijack.mfe.gateway.model.vo.UserVo;
import wang.hijack.mfe.gateway.security.model.LoginPassword;
import wang.hijack.mfe.gateway.security.model.exception.PasswordException;
import wang.hijack.mfe.gateway.security.service.AuthUserService;
import wang.hijack.mfe.gateway.service.UserService;
import wang.hijack.mfe.gateway.util.UserHolder;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Jack
 */
@RestController
@RequestMapping("system")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthUserService authUserService;

    @GetMapping("users")
    public Page<UserVo> userList(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String orgNo,
                                 @RequestParam(required = false) Boolean enabled,
                                 @RequestParam(required = false) List<Long> createTime,
                                 Pageable pageable) {
        return userService.list(name, orgNo, enabled, createTime, pageable.previousOrFirst());
    }

    @PostMapping("users")
    public ResponseEntity<UserVo> insert(@RequestBody UserDto dto) {
        var userVo = userService.insert(dto);
        return ResponseEntity.ok(userVo);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserVo> update(@PathVariable Long id, @RequestBody UserDto dto) throws EntityNotExistException {
        dto.setId(id);
        var userVo = userService.update(dto);
        return ResponseEntity.ok(userVo);
    }

    @DeleteMapping("users")
    public ResponseEntity<String> delete(@RequestParam List<Long> ids) {
        userService.deleteAll(ids);
        return ResponseEntity.ok("成功");
    }

    @GetMapping("users/me")
    public ResponseEntity<UserVo> me() {
        var user = UserHolder.user();
        if (user != null) {
            return ResponseEntity.ok(userService.findByUserNo(user.getUserNo()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("users/exports")
    public void download(HttpServletResponse response) {
        userService.download(response);
    }

    @PutMapping("users/password")
    public void password(@RequestBody LoginPassword password) throws PasswordException {
        authUserService.changePassword(password);
    }

}
