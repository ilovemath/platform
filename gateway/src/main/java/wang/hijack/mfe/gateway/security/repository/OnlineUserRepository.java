package wang.hijack.mfe.gateway.security.repository;

import wang.hijack.mfe.gateway.security.model.OnlineUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Jack
 */
public interface OnlineUserRepository extends JpaRepository<OnlineUser, String> {
    /**
     * 根据用户号查询当前登录用户
     *
     * @param userNo\
     * @return \
     */
    List<OnlineUser> findByUserNo(String userNo);
}
