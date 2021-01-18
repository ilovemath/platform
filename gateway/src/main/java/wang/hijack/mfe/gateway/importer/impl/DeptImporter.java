package wang.hijack.mfe.gateway.importer.impl;

import wang.hijack.mfe.gateway.importer.Importer;
import wang.hijack.mfe.gateway.model.dto.DeptDto;
import wang.hijack.mfe.gateway.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jack
 */
@Component
public class DeptImporter implements Importer {
    @Autowired
    private DeptService service;
    private List<DeptDto> deptList = Arrays.asList(
            DeptDto.builder().id(1L).level(0).orgName("总行").orgNo("001").build(),
            DeptDto.builder().id(2L).level(1).orgName("北京分行").orgNo("002").build(),
            DeptDto.builder().id(3L).level(2).orgName("海淀分行").orgNo("003").build()
    );

    @Override
    public void importData() {
        deptList.forEach(service::upsert);
    }

    @Override
    public int order() {
        return 1;
    }
}
