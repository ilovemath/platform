package wang.hijack.mfe.db.importer.impl;

import wang.hijack.mfe.db.importer.Importer;
import wang.hijack.mfe.db.model.dto.DeptDto;
import wang.hijack.mfe.db.service.DeptService;
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
            DeptDto.builder().id(1L).level(0).deptName("总行").deptNo("001").build(),
            DeptDto.builder().id(2L).level(1).deptName("北京分行").deptNo("002").build(),
            DeptDto.builder().id(3L).level(2).deptName("海淀分行").deptNo("003").build()
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
