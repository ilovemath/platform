package wang.hijack.mfe.gateway.importer.impl;

import wang.hijack.mfe.gateway.importer.Importer;
import wang.hijack.mfe.gateway.model.dto.AppDto;
import wang.hijack.mfe.gateway.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jack
 */
@Component
public class AppImporter implements Importer {
    @Autowired
    private AppService service;
    private List<AppDto> appList = Arrays.asList(
            AppDto.builder().id(1L).path("system").name("系统")
                    .entry("http://localhost:6060")
                    .service("http://localhost:8081").build(),
            AppDto.builder().id(2L).path("mbank").name("个人掌银")
                    .entry("http://localhost:6061")
                    .service("http://localhost:8082").build(),
            AppDto.builder().id(3L).path("perbank").name("个人网银")
                    .entry("http://localhost:6062")
                    .service("http://localhost:8083").build()
    );

    @Override
    public void importData() {
        appList.forEach(service::upsert);
    }

    @Override
    public int order() {
        return 0;
    }
}
