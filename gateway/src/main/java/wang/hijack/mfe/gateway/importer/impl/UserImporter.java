package wang.hijack.mfe.gateway.importer.impl;

import wang.hijack.mfe.gateway.importer.Importer;
import wang.hijack.mfe.gateway.model.dto.UserDto;
import wang.hijack.mfe.gateway.model.vo.RoleVo;
import wang.hijack.mfe.gateway.service.RoleService;
import wang.hijack.mfe.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Component
public class UserImporter implements Importer {
    @Autowired
    private UserService service;
    @Autowired
    private RoleService roleService;

    @Override
    public void importData() {
        Set<Long> roleIds = roleService.listAll().stream().map(RoleVo::getId).collect(Collectors.toSet());
        UserDto user = UserDto.builder()
                .id(1L).userNo("001").orgNo("001")
                .nickname("超级管理员").avatar("avatar.png")
                .phone("15901234567").email("jack@abchina.com")
                .enabled(true).locked(false).roleIds(roleIds).build();
        service.upsert(user);
    }

    @Override
    public int order() {
        return 4;
    }
}
