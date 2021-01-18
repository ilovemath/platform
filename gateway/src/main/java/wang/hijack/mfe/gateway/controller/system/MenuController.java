package wang.hijack.mfe.gateway.controller.system;

import wang.hijack.mfe.gateway.model.vo.MenuVo;
import wang.hijack.mfe.gateway.security.model.annotation.Authenticated;
import wang.hijack.mfe.gateway.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jack
 */
@RestController
@RequestMapping("system")
public class MenuController {
    @Autowired
    private MenuService service;

    @Authenticated
    @GetMapping("menus")
    List<MenuVo> getMenu() {
        return service.getMenuTree();
    }
}
