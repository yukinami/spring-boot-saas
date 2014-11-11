package yukinami.example.saas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import yukinami.example.saas.annotation.RootResource;
import yukinami.example.saas.annotation.TenantResource;

/**
 * Created by snowblink on 14/11/6.
 */
public class TenantResolveInterceptor extends HandlerInterceptorAdapter {

    public static final String TENANT_BUSINESS_NAME_KEY = "businessName";

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessName = resolve(urlPathHelper.getServletPath(request));
        request.setAttribute(TENANT_BUSINESS_NAME_KEY, businessName);

        // restrict the access
        HandlerMethod method = (HandlerMethod) handler;
        TenantResource tenantResource = method.getMethodAnnotation(TenantResource.class);
        RootResource rootResource = method.getMethodAnnotation(RootResource.class);

        boolean isRootResource = false;

        // get annotation from class when no annotation is specified
        if (tenantResource == null && rootResource == null) {
            tenantResource = AnnotationUtils.findAnnotation(method.getBeanType(), TenantResource.class);
            rootResource = AnnotationUtils.findAnnotation(method.getBeanType(), RootResource.class);
        }

        // still with no annotation, set default
        if (tenantResource == null && rootResource == null) {
            isRootResource = true;
        }

        // tenant resource
        if (tenantResource != null && StringUtils.isEmpty(businessName)) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }

        // root resource
        if ((rootResource != null || isRootResource) && !StringUtils.isEmpty(businessName)) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * resolve to the actual business name
     *
     * @param servletPath
     * @return
     */
    protected String resolve(String servletPath) {
        if (servletPath.length() > 0 ) {
            return servletPath.substring(1);
        }

        return "";
    }

}
