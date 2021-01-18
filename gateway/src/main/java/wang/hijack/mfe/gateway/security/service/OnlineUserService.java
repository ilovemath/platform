package wang.hijack.mfe.gateway.security.service;

import cn.hutool.core.date.DateUtil;
import wang.hijack.mfe.gateway.repository.DeptRepository;
import wang.hijack.mfe.gateway.security.config.Constants;
import wang.hijack.mfe.gateway.security.config.SecurityProperties;
import wang.hijack.mfe.gateway.security.model.OnlineUser;
import wang.hijack.mfe.gateway.security.repository.OnlineUserRepository;
import wang.hijack.mfe.gateway.util.FileUtils;
import wang.hijack.mfe.gateway.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jack
 */
@Slf4j
@Service
public class OnlineUserService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private OnlineUserRepository repository;
    @Autowired
    private SecurityProperties properties;
    @Autowired
    private DeptRepository deptRepository;

    /**
     * 根据token获取用户信息
     *
     * @param token /
     * @return OnlineUser
     */
    public OnlineUser getByToken(String token) {
        return repository.findById(token).orElse(null);
    }

    /**
     * 保存在线用户信息
     *
     * @param token   /
     * @param user    /
     * @param request /
     */
    public OnlineUser save(String token, Object user, HttpServletRequest request) {
        OnlineUser onlineUser = new OnlineUser(token, user);
        onlineUser.setIp(RequestUtils.getIp(request));
        onlineUser.setBrowser(RequestUtils.getBrowser(request));
        repository.save(onlineUser);
        setExpiration(token);
        return onlineUser;
    }

    public void update(OnlineUser user) {
        repository.save(user);
    }

    /**
     * 查询全部数据，不分页
     *
     * @param filter /
     * @return /
     */
    public List<OnlineUser> getAll(String filter) {
        List<OnlineUser> onlineUsers = repository.findAll();
        if (StringUtils.isNotBlank(filter)) {
            onlineUsers = onlineUsers.stream()
                    .filter(user -> user != null && user.toString().contains(filter))
                    .collect(Collectors.toList());
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }

    /**
     * 退出登录
     *
     * @param token /
     */
    public void logout(String token) {
        repository.deleteById(token);
    }

    /**
     * 导出
     *
     * @param filter   /
     * @param response /
     * @throws IOException /
     */
    public void download(String filter, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OnlineUser user : getAll(filter)) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("姓名", user.getNickname());
            map.put("机构号", user.getOrgNo());
            map.put("用户号", user.getUserNo());
            map.put("登录IP", user.getIp());
            map.put("浏览器", user.getBrowser());
            Date date = new Date(user.getLoginTime());
            String format = "yyyy年MM月dd日 HH:mm:ss";
            map.put("登录日期", DateUtil.format(date, format));
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     *
     * @param userNo 用户号
     */
    public void checkLoginUser(String userNo) {
        List<OnlineUser> onlineUsers = repository.findByUserNo(userNo);
        if (onlineUsers != null && !onlineUsers.isEmpty()) {
            onlineUsers.forEach(onlineUser -> {
                if (onlineUser != null) {
                    logout(onlineUser.getToken());
                }
            });
        }
    }

    private void setExpiration(String token) {
        String key = String.format("%s:%s", Constants.ONLINE_USER, token);
        redisTemplate.expire(key, properties.getTimeoutSeconds(), TimeUnit.SECONDS);
    }

}
