package wang.hijack.mfe.gateway.controller.system;

import wang.hijack.mfe.gateway.model.vo.AppVo;
import wang.hijack.mfe.gateway.service.AppService;
import wang.hijack.mfe.gateway.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@RestController
@RequestMapping("system")
public class AppController {
    @Autowired
    private AppService service;

    @GetMapping("apps")
    List<AppVo> list() {
        return UserHolder.apps().stream().map(AppVo::new).collect(Collectors.toList());
    }

}
