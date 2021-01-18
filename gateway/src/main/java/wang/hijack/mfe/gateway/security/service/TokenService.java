package wang.hijack.mfe.gateway.security.service;

import org.springframework.stereotype.Service;

/**
 * @author Jack
 */
@Service
public class TokenService {
    /**
     * 获取token
     *
     * @param userNo /
     * @return token
     */
    public String getToken(String userNo) {
        return userNo;
    }
}
