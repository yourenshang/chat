package syr.design.chat.filter;

import org.springframework.stereotype.Component;
import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.model.Result;
import syr.design.chat.model.Users;
import syr.design.chat.utils.JJWTUtil;
import syr.design.chat.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
public class ChatFilter implements Filter {

    @Resource
    private JJWTUtil jjwtUtil;

    @Resource
    private RedisUtil redisUtil;

    private void sendFailed(ServletResponse servletResponse) throws IOException{
        servletResponse.setContentType("text/html;charset=utf-8");
        Result result = new Result();
        result.setCode(EnumResultCode.FAIL.value());
        result.setMessage("无效令牌");
        result.setInfo(null);
        servletResponse.getWriter().write(result.toString());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean flag = true;
        for (String alowyUrl : jjwtUtil.getAllowUrls()){
            if (request.getRequestURI().contains(alowyUrl)){
                flag = false;
                break;
            }
        }
        if (flag) {
            String token = request.getHeader("token");
            if (token == null) {
                sendFailed(servletResponse);
                return;
            }
            Map<String, Object> tokenMap = jjwtUtil.checkToken(token);
            if (tokenMap == null || tokenMap.size() <= 0) {
                sendFailed(servletResponse);
                return;
            }
            String userId = tokenMap.get("userId").toString();
            if (!redisUtil.hasKeyObject(userId + "user")) {
                sendFailed(servletResponse);
                return;
            }
            Users users = (Users) redisUtil.getObject(userId + "user");
            redisUtil.setObject(userId + "user", users, 3000);
            filterChain.doFilter(new ApiHttpServletRequest(request, userId), servletResponse);
            return ;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
