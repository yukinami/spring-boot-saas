package yukinami.example.saas.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by snowblink on 14/11/10.
 */
public interface InventoryRepository extends CrudRepository<Inventory, Long > {
}
