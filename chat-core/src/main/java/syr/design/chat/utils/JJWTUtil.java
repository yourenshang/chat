package syr.design.chat.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;


/**
 * JWT 工具类
 *
 * @author cyan
 * @since 2019/3/7
 */
@Slf4j
public class JJWTUtil {

    private String signKey;

    private String subject;

    private String issuer;

    private int tokenRefreshInterval;

    private List<String> allowUrls;

    public List<String> getAllowUrls() {
        return this.allowUrls;
    }

    public JJWTUtil(String signKey, String subject, String issuer, int tokenRefreshInterval, String urls) {
        this.signKey = signKey;
        this.subject = subject;
        this.issuer = issuer;
        this.tokenRefreshInterval = tokenRefreshInterval;
        allowUrls = Arrays.asList(StringUtils.splitByWholeSeparatorPreserveAllTokens(urls, ","));
    }

    /**
     * 签名算法
     */
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;


    /**
     * @param algorithm 加解密类型
     * @param signKey   签名密钥
     */
    public SecretKey generateKey(SignatureAlgorithm algorithm, String signKey) {
        byte[] bytes = Base64.getDecoder().decode(signKey);
        return new SecretKeySpec(bytes, algorithm.getJcaName());
    }

    /**
     * 生成jwt token （有过期时间）
     *
     * @param userId        用户id
     * @param effectiveTime 生效时间
     * @param duration      有效时间
     */
    public String generateToken(String userId, Date effectiveTime, Integer duration) {
        effectiveTime = effectiveTime == null ? DateTime.now() : effectiveTime;
        DateTime expireTime = null;
        if (duration != null) {
            expireTime = DateTime.of(effectiveTime).offsetNew(DateField.MINUTE, duration);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer(issuer)
                .setId(userId).setIssuedAt(effectiveTime).setExpiration(expireTime)
                .signWith(algorithm, generateKey(algorithm, signKey))
                .compact();
    }

    /**
     * 生成jwt token （无过期时间）
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer(issuer).setIssuedAt(DateTime.now())
                .setId(userId).signWith(algorithm, generateKey(algorithm, signKey))
                .compact();
    }

    /**
     * 验证jwt token
     */
    public Map<String, Object> checkToken(String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (Objects.nonNull(token) && !token.isEmpty()) {
                Claims claims = Jwts.parser().setSigningKey(generateKey(algorithm, signKey)).parseClaimsJws(token).getBody();
                if (claims.getSubject().equals(subject) && claims.getIssuer().equals(issuer)) {
                    map.put("userId", claims.getId());
                    map.put("isExpired", isExpired(claims.getIssuedAt()));
                    return map;
                }
            }
        } catch (Exception e) {
            log.error("checkToken error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 验证token是否过期
     */
    private boolean isExpired(Date date) {
        return DateTime.of(date).offsetNew(DateField.MINUTE, tokenRefreshInterval).isBefore(DateTime.now());
    }

}