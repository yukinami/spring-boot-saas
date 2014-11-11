package yukinami.example.saas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import yukinami.example.saas.util.TenantContextHolder;

/**
 * Created by snowblink on 14/11/7.
 */
public class RoutingDataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessName = (String)request.getAttribute(TenantResolveInterceptor.TENANT_BUSINESS_NAME_KEY);
        TenantContextHolder.setBusinessName(businessName);
        return true;
    }
}
