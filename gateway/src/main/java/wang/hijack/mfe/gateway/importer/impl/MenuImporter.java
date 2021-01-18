package wang.hijack.mfe.gateway.importer.impl;

import cn.hutool.core.io.IoUtil;
import wang.hijack.mfe.gateway.importer.Importer;
import wang.hijack.mfe.gateway.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Jack
 */
@Component
public class MenuImporter implements Importer {
    @Autowired
    private MenuService service;

    private String readJson() {
        InputStream inputStream = this.getClass().getResourceAsStream("/menu.json");
        return IoUtil.read(inputStream, "utf-8");
    }

    @Override
    public void importData() {
        service.readJsonTree(readJson()).forEach(service::upsert);
    }

    @Override
    public int order() {
        return 2;
    }
}
