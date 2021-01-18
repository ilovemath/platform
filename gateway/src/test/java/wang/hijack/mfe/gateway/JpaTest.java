package wang.hijack.mfe.gateway;

import wang.hijack.mfe.gateway.model.entity.AppEntity;
import wang.hijack.mfe.gateway.model.entity.MenuEntity;
import wang.hijack.mfe.gateway.model.entity.RoleEntity;
import wang.hijack.mfe.gateway.model.entity.UserEntity;
import wang.hijack.mfe.gateway.repository.AppRepository;
import wang.hijack.mfe.gateway.repository.MenuRepository;
import wang.hijack.mfe.gateway.repository.RoleRepository;
import wang.hijack.mfe.gateway.repository.UserRepository;
import wang.hijack.mfe.gateway.security.config.Constants;
import wang.hijack.mfe.gateway.security.model.OnlineUser;
import wang.hijack.mfe.gateway.security.repository.OnlineUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class JpaTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private OnlineUserRepository onlineUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertSomeUser() {
        AppEntity appEntity = new AppEntity();
        appEntity.setPath("app");
        appRepository.save(appEntity);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("admin");
        roleRepository.save(roleEntity);
        UserEntity userEntity = new UserEntity();
        userEntity.setOrgNo("123");
        userEntity.setUserNo("123");
        userEntity.setPassword(passwordEncoder.encode("123"));
        userEntity.addRoles(roleEntity);
        userRepository.save(userEntity);
    }

    @Test
    public void test() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setTitle("你好");
        menuEntity.setParentId(1L);
        menuRepository.save(menuEntity);
        AppEntity appEntity = new AppEntity();
        appRepository.save(appEntity);
    }

    @Test
    public void testRedisJpa() {
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setUserNo("user1");
        onlineUser.setBrowser("Chrome");
        onlineUser.setIp("127.0.0.1");
        onlineUser.setToken("1");
        onlineUserRepository.save(onlineUser);
        onlineUser.setBrowser("Chrome");
        onlineUser.setIp("127.0.0.1");
        onlineUser.setToken("2");
        onlineUser.setUserNo("user2");
        onlineUserRepository.save(onlineUser);
        template.expire(Constants.ONLINE_USER + ":1", 5, TimeUnit.SECONDS);
    }

}
