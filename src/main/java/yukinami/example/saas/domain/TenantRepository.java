package yukinami.example.saas.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by snowblink on 14/11/6.
 */
public interface TenantRepository extends CrudRepository<Tenant, Long> {
}
