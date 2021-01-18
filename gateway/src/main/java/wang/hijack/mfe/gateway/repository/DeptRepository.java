package wang.hijack.mfe.gateway.repository;

import wang.hijack.mfe.gateway.model.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Jack
 */
public interface DeptRepository extends JpaRepository<DeptEntity, Long> {
    /**
     * 根据机构名模糊查询
     *
     * @param name /
     * @return /
     */
    List<DeptEntity> findByOrgNameLike(String name);
}
