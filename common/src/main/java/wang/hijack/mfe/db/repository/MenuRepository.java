package wang.hijack.mfe.db.repository;

import wang.hijack.mfe.db.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jack
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
