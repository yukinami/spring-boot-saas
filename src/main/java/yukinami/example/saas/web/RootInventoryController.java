package yukinami.example.saas.web;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yukinami.example.saas.annotation.RootResource;
import yukinami.example.saas.annotation.TenantResource;
import yukinami.example.saas.domain.Inventory;
import yukinami.example.saas.domain.InventoryRepository;

/**
 * Created by snowblink on 14-10-14.
 */
@RestController
@RequestMapping("/root_inventories")
@RootResource
public class RootInventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @RequestMapping("/")
    public String list() {

        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);
        Iterable<Inventory> inventories = inventoryRepository.findAll();

        for (Inventory inventory : inventories) {
            writer.println(inventory.getName());
        }

        return string.toString();
    }

    @RequestMapping("/tenant")
    @TenantResource
    public String tenantList() {

        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);
        Iterable<Inventory> inventories = inventoryRepository.findAll();

        for (Inventory inventory : inventories) {
            writer.println(inventory.getName());
        }

        return string.toString();
    }

}
