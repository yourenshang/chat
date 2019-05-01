package syr.design.chat.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class ApiHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String[]> paramters = new TreeMap<String, String[]>();

    ApiHttpServletRequest(HttpServletRequest request, String userId) {
        super(request);
        paramters.putAll(super.getParameterMap());
        String [] userIds = new String[1];
        userIds[0] = userId;
        paramters.put("userId", userIds);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(paramters);
    }

    @Override
    public String getParameter(String name) {
        String [] strings = getParameterMap().get(name);
        if (strings != null){
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return getParameterMap().get(name);
    }
}
