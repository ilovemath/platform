package wang.hijack.mfe.db.importer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author Jack
 */
@Slf4j
@Component
public class ImportRunner {
    private List<Importer> importers;

    public ImportRunner(List<Importer> importers) {
        this.importers = importers;
    }

    @PostConstruct
    public void run() {
        try {
            Collections.sort(importers);
            importers.forEach(Importer::importData);
        } catch (Exception e) {
            log.info("错误详情：", e);
        }
    }
}
