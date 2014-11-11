package yukinami.example.saas;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import yukinami.example.saas.domain.Tenant;
import yukinami.example.saas.domain.TenantRepository;
import yukinami.example.saas.interceptor.RoutingDataSourceInterceptor;
import yukinami.example.saas.interceptor.TenantResolveInterceptor;

/**
 * Created by snowblink on 14/11/6.
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceProperties properties;

    @Autowired
    private TenantRepository tenantRepository;


    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Autowired(required = false)
    private MultipartConfigElement multipartConfig;

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
    public ServletRegistrationBean dispatcherServletRegistration() {
        Iterable<Tenant> tenants = this.tenantRepository.findAll();

        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet(), getServletMappings(tenants));
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        if (this.multipartConfig != null) {
            registration.setMultipartConfig(this.multipartConfig);
        }

        registerDataSource(tenants);

        return registration;
    }


    protected void registerDataSource(Iterable<Tenant> tenants) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (Tenant tenant : tenants) {

            DataSourceBuilder factory = DataSourceBuilder
                    .create(this.properties.getClassLoader())
                    .url(this.properties.getUrl())
                    .username(tenant.getDbu())
                    .password(tenant.getEdbpwd());


            targetDataSources.put(tenant.getBusinessName(), factory.build());
        }

        ((AbstractRoutingDataSource) this.dataSource).setTargetDataSources(targetDataSources);
        ((AbstractRoutingDataSource) this.dataSource).afterPropertiesSet();
    }

    protected String[] getServletMappings(Iterable<Tenant> tenants) {
        List<String> mappings = new ArrayList<>();
        for (Tenant tenant : tenants) {
            mappings.add("/" + tenant.getBusinessName() + "/*");
        }

        mappings.add("/*");

        return mappings.toArray(new String[0]);
    }

    /**
     * Encrypt the business name to prevent from abusing accessing.
     * Need to resolve to the actual business name in {@link yukinami.example.saas.interceptor.TenantResolveInterceptor#resolve(String)}
     *
     * @param businessName
     * @return
     */
    protected String encryptBusinessName(String businessName) {
        return businessName;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantResolveInterceptor());
        registry.addInterceptor(new RoutingDataSourceInterceptor());
    }
}
