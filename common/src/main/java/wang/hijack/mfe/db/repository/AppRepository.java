package wang.hijack.mfe.db.repository;


import wang.hijack.mfe.db.model.entity.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jack
 */
public interface AppRepository extends JpaRepository<AppEntity, Long> {
    /**
     * 根据应用路径查找
     *
     * @param path \
     * @return \
     */
    AppEntity findByPath(String path);
}
