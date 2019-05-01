package syr.design.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import syr.design.chat.utils.JJWTUtil;

@Configuration
public class JwtConfig {

    @Bean(name = "JJWTUtil")
    public JJWTUtil jjwtUtil(@Value("${jwt.signKey}") String signKey,
                             @Value("${jwt.subject}") String subject,
                             @Value("${jwt.issuer}") String issuer,
                             @Value("${jwt.allowUrls}") String allowUrls,
                             @Value("${jwt.tokenRefreshInterval}") int tokenRefreshInterval) {
        return new JJWTUtil(signKey, subject, issuer, tokenRefreshInterval, allowUrls);
    }

}
