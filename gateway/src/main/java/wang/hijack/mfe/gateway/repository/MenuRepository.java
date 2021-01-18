package wang.hijack.mfe.gateway.repository;

import wang.hijack.mfe.gateway.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jack
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
