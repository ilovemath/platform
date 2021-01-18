package wang.hijack.mfe.gateway.repository;

import wang.hijack.mfe.gateway.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jack
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    /**
     * 根据机构号和客户号查询用户
     *
     * @param orgNo/
     * @param userNo/
     * @return UserEntity
     */
    UserEntity findByOrgNoAndUserNo(String orgNo, String userNo);

    /**
     * 根据客户号查询用户
     *
     * @param userNo /
     * @return /
     */
    UserEntity findByUserNo(String userNo);

    /**
     * 根据用户名进行分页筛选
     *
     * @param name\
     * @param pageable\
     * @return \
     */
    Page<UserEntity> findByNicknameLike(String name, Pageable pageable);
}
