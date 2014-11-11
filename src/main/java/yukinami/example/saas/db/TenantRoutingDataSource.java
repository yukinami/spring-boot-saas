package yukinami.example.saas.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import yukinami.example.saas.util.TenantContextHolder;

/**
 * Created by snowblink on 14/11/7.
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getBusinessName();
    }
}
