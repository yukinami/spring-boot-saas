package yukinami.example.saas;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import yukinami.example.saas.db.TenantRoutingDataSource;
import yukinami.example.saas.domain.Tenant;
import yukinami.example.saas.domain.TenantRepository;

/**
 * Created by snowblink on 14/11/7.
 */
@Configuration
public class DbConfig {

    @Autowired
    private DataSourceProperties properties;

    public DataSource defaultDataSource(){
        return DataSourceBuilder
                .create(this.properties.getClassLoader())
                .url(this.properties.getUrl())
                .username(this.properties.getUsername())
                .password(this.properties.getPassword()).build();
    }

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultDataSource());
        routingDataSource.setTargetDataSources(new HashMap<Object, Object>());
        return routingDataSource;
    }
}
