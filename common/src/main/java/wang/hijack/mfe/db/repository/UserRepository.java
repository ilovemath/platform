package wang.hijack.mfe.db.repository;

import wang.hijack.mfe.db.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jack
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    /**
     * 根据用户名查询
     *
     * @param username /
     * @return /
     */
    UserEntity findByUsername(String username);

    /**
     * 根据用户名进行分页筛选
     *
     * @param name /
     * @param pageable /
     * @return /
     */
    Page<UserEntity> findByNicknameLike(String name, Pageable pageable);
}
