package wang.hijack.mfe.gateway.controller;

import wang.hijack.mfe.gateway.model.vo.VisitsVo;
import wang.hijack.mfe.gateway.security.model.annotation.Authenticated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Jack
 */
@RestController
@RequestMapping("monitor")
public class MonitorController {
    private long r() {
        return new Random().nextInt(10000000);
    }

    private VisitsVo getOne() {
        return new VisitsVo(r(), r(), r(), r());
    }

    @Authenticated
    @GetMapping("visits")
    public VisitsVo visits() {
        return getOne();
    }

    @Authenticated
    @PostMapping("visits")
    public VisitsVo incVisits() {
        return getOne();
    }

}
