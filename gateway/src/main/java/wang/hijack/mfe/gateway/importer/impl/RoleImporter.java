package wang.hijack.mfe.gateway.importer.impl;

import wang.hijack.mfe.gateway.importer.Importer;
import wang.hijack.mfe.gateway.model.dto.RoleDto;
import wang.hijack.mfe.gateway.model.vo.MenuVo;
import wang.hijack.mfe.gateway.service.AppService;
import wang.hijack.mfe.gateway.service.MenuService;
import wang.hijack.mfe.gateway.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Component
public class RoleImporter implements Importer {
    @Autowired
    private RoleService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    private AppService appService;

    @Override
    public void importData() {
        Set<Long> menuIds = menuService.listAll().stream().map(MenuVo::getId).collect(Collectors.toSet());
        RoleDto admin = RoleDto.builder().id(1L).menuIds(menuIds).name("超级管理员").build();
        service.upsert(admin);
    }

    @Override
    public int order() {
        return 3;
    }
}
